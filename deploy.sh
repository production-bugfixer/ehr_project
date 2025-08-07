#!/bin/bash

echo "🚀 Starting all EHR services sequentially..."

# List of services: name port jar_path log_file
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
echo "🔪 Killing all processes using required ports..."

# First pass: Kill any existing processes on the needed ports
for service in "${services[@]}"; do
  read -r name port jar log <<< "$service"
  pid=$(lsof -t -i:$port)
  if [ -n "$pid" ]; then
    echo "⚠️ Port $port for $name is in use by PID $pid. Killing..."
    kill -9 "$pid"
    sleep 2
  else
    echo "✅ Port $port for $name is already free."
  fi
done

echo "🟢 All required ports are now free."
echo "───────────────────────────────────────────────"

# Function to start a service
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

  echo "❌ $name failed to start on port $port after waiting."
}

# Second pass: Start all services
for service in "${services[@]}"; do
  read -r name port jar log <<< "$service"
  echo "───────────────────────────────────────────────"
  start_service "$name" "$port" "$jar" "$log"
done

echo "✅ All services processed."
