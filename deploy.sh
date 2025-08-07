#!/bin/bash

echo "🚀 Starting all EHR services sequentially..."

# Define services: name, port, jar path, log file
services=(
  "registerservice 8761 /var/lib/jenkins/workspace/spring-boot-pipeline/registerservice/target/registerservice-0.0.1-SNAPSHOT.jar registerservice.log"
  "authenticate 8082 /var/lib/jenkins/workspace/spring-boot-pipeline/authenticate/target/authenticate-0.0.1-SNAPSHOT.jar authenticate.log"
  "bloodreport 8083 /var/lib/jenkins/workspace/spring-boot-pipeline/bloodreport/target/bloodreport-1.0.0.jar bloodreport.log"
  "doctor 8084 /var/lib/jenkins/workspace/spring-boot-pipeline/doctor/target/doctor-0.0.1-SNAPSHOT.jar doctor.log"
  "hospitalgateway 9090 /var/lib/jenkins/workspace/spring-boot-pipeline/hospitalgatway/target/hospitalgateway-0.0.1-SNAPSHOT.jar hospitalgateway.log"
  "microbiology 8086 /var/lib/jenkins/workspace/spring-boot-pipeline/microbiology/target/microbiology-1.0.0.jar microbiology.log"
  "patient 8087 /var/lib/jenkins/workspace/spring-boot-pipeline/patient/target/patient-0.0.1-SNAPSHOT.jar patient.log"
)

echo "───────────────────────────────────────────────"
echo "📋 Listing running ports before kill:"
sudo lsof -i -P -n | grep LISTEN | grep -E "$(IFS=\|; echo "${services[*]}" | grep -oP '\d{4,5}' | paste -sd '|' -)"

echo "───────────────────────────────────────────────"
echo "🔪 Killing all EHR service processes by JAR name..."

for service in "${services[@]}"; do
  read -r name port jar log <<< "$service"
  jar_name=$(basename "$jar")

  echo "☠️ Killing service: $name ($jar_name)"
  sudo pkill -f "$jar_name" && echo "✅ Killed $jar_name" || echo "ℹ️ No running process found for $jar_name"
done

echo "───────────────────────────────────────────────"
echo "🔪 Killing any process still holding service ports..."

for service in "${services[@]}"; do
  read -r name port jar log <<< "$service"
  pid=$(sudo lsof -t -i:$port)
  if [ -n "$pid" ]; then
    echo "⚠️ Port $port ($name) is still used by PID $pid. Killing..."
    sudo kill -9 "$pid" && echo "✅ Killed PID $pid for port $port"
    sleep 1
  fi
done

echo "───────────────────────────────────────────────"
echo "📋 Listing ports after kill (should be clean):"
sudo lsof -i -P -n | grep LISTEN | grep -E "$(IFS=\|; echo "${services[*]}" | grep -oP '\d{4,5}' | paste -sd '|' -)" || echo "✅ All targeted ports are free."

echo "───────────────────────────────────────────────"
echo "🟢 Starting services in correct order..."

# Function to start service and check readiness
start_service() {
  local name=$1
  local port=$2
  local jar_path=$3
  local log_file=$4

  echo "▶️ Starting $name on port $port..."
  nohup stdbuf -oL -eL java -Dspring.output.ansi.enabled=ALWAYS \
       -Djava.util.logging.ConsoleHandler.level=ALL \
       -jar "$jar_path" --server.port=$port > "$log_file" 2>&1 &

  echo "⏳ Waiting for $name to start on port $port..."
  for i in {1..20}; do
    if nc -z localhost $port; then
      echo "✅ $name is running on port $port."
      echo "📄 Logs: tail -f $log_file"
      return
    fi
    sleep 2
  done

  echo "❌ $name failed to start on port $port."
}

# Define strict startup order
ordered_start=("registerservice" "hospitalgateway")

# Start required services first
for target in "${ordered_start[@]}"; do
  for service in "${services[@]}"; do
    read -r name port jar log <<< "$service"
    if [[ "$name" == "$target" ]]; then
      echo "───────────────────────────────────────────────"
      start_service "$name" "$port" "$jar" "$log"
    fi
  done
done

# Start remaining services
for service in "${services[@]}"; do
  read -r name port jar log <<< "$service"
  if [[ ! " ${ordered_start[*]} " =~ " $name " ]]; then
    echo "───────────────────────────────────────────────"
    start_service "$name" "$port" "$jar" "$log"
  fi
done

echo "✅ All services started successfully."
