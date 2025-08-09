#!/bin/bash

echo "🚀 Starting full stop-and-redeploy script for EHR services..."

services=(
  "registerservice 8761 /var/lib/jenkins/workspace/spring-boot-pipeline/registerservice/target/registerservice-0.0.1-SNAPSHOT.jar"
  "authenticate 8082 /var/lib/jenkins/workspace/spring-boot-pipeline/authenticate/target/authenticate-0.0.1-SNAPSHOT.jar"
  "bloodreport 8083 /var/lib/jenkins/workspace/spring-boot-pipeline/bloodreport/target/bloodreport-1.0.0.jar"
  "doctor 8084 /var/lib/jenkins/workspace/spring-boot-pipeline/doctor/target/doctor-0.0.1-SNAPSHOT.jar"
  "hospitalgateway 9090 /var/lib/jenkins/workspace/spring-boot-pipeline/hospitalgatway/target/hospitalgateway-0.0.1-SNAPSHOT.jar"
  "microbiology 8086 /var/lib/jenkins/workspace/spring-boot-pipeline/microbiology/target/microbiology-1.0.0.jar"
  "patient 8087 /var/lib/jenkins/workspace/spring-boot-pipeline/patient/target/patient-0.0.1-SNAPSHOT.jar"
)

declare -A debug_services=(
  ["registerservice"]=5001
  ["authenticate"]=5002
  ["bloodreport"]=5003
  ["doctor"]=5004
  ["hospitalgateway"]=5005
  ["microbiology"]=5006
  ["patient"]=5007
)

echo "───────────────────────────────────────────────"
echo "🔍 Checking UFW firewall status..."

ufw_status=$(sudo ufw status | head -1)

if [[ "$ufw_status" == "Status: inactive" ]]; then
  echo "ℹ️ UFW firewall is disabled. No need to open ports."
else
  echo "🛡️ UFW firewall is active. Opening required ports..."

  # Open all service ports
  for service in "${services[@]}"; do
    read -r name port jar <<< "$service"
    echo "🔓 Allowing TCP port $port for $name"
    sudo ufw allow "$port/tcp"
  done

  # Open all debug ports
  for debug_port in "${debug_services[@]}"; do
    echo "🔓 Allowing TCP debug port $debug_port"
    sudo ufw allow "$debug_port/tcp"
  done

  echo "🔄 Reloading UFW..."
  sudo ufw reload
fi

echo "───────────────────────────────────────────────"
echo "🔪 Stopping all services..."

for service in "${services[@]}"; do
  read -r name port jar <<< "$service"
  jar_name=$(basename "$jar")

  echo "Stopping $name (JAR: $jar_name)..."
  sudo pkill -f "$jar_name" && echo "✅ Killed $jar_name" || echo "ℹ️ No running process for $jar_name"

  pids=$(sudo lsof -t -i:$port)
  if [ -n "$pids" ]; then
    echo "⚠️ Killing process(es) on port $port: $pids"
    sudo kill -9 $pids
  fi
done

echo "✅ All services stopped and ports freed."

echo "───────────────────────────────────────────────"
echo "🟢 Starting all services with remote debugging..."

for service in "${services[@]}"; do
  read -r name port jar <<< "$service"
  log_file="${name}.log"
  debug_port=${debug_services[$name]}

  debug_args="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:$debug_port"

  echo "▶️ Starting $name on port $port with debug on port $debug_port..."
  nohup stdbuf -oL -eL java $debug_args \
       -Dspring.output.ansi.enabled=ALWAYS \
       -Djava.util.logging.ConsoleHandler.level=ALL \
       -jar "$jar" --server.port=$port > "$log_file" 2>&1 &

  echo "⏳ Waiting for $name to start on port $port..."
  for i in {1..20}; do
    if nc -z localhost $port; then
      echo "✅ $name is running on port $port."
      echo "📄 Logs: tail -f $log_file"
      break
    fi
    sleep 2
  done
done

echo "───────────────────────────────────────────────"
echo "✅ All services restarted successfully with remote debug enabled."
