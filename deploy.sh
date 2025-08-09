#!/bin/bash

echo "🚀 Starting full stop-and-redeploy script for EHR services..."

# Define services: name, HTTP port, JAR path
services=(
  "registerservice 8761 /var/lib/jenkins/workspace/spring-boot-pipeline/registerservice/target/registerservice-0.0.1-SNAPSHOT.jar"
  "authenticate 8082 /var/lib/jenkins/workspace/spring-boot-pipeline/authenticate/target/authenticate-0.0.1-SNAPSHOT.jar"
  "bloodreport 8083 /var/lib/jenkins/workspace/spring-boot-pipeline/bloodreport/target/bloodreport-1.0.0.jar"
  "doctor 8084 /var/lib/jenkins/workspace/spring-boot-pipeline/doctor/target/doctor-0.0.1-SNAPSHOT.jar"
  "hospitalgateway 9090 /var/lib/jenkins/workspace/spring-boot-pipeline/hospitalgatway/target/hospitalgateway-0.0.1-SNAPSHOT.jar"
  "microbiology 8086 /var/lib/jenkins/workspace/spring-boot-pipeline/microbiology/target/microbiology-1.0.0.jar"
  "patient 8087 /var/lib/jenkins/workspace/spring-boot-pipeline/patient/target/patient-0.0.1-SNAPSHOT.jar"
)

# Map service names to debug ports
declare -A debug_services=(
  ["registerservice"]=5001
  ["authenticate"]=5002
  ["bloodreport"]=5003
  ["doctor"]=5004
  ["hospitalgateway"]=5005
  ["microbiology"]=5006
  ["patient"]=5007
)

open_firewall_ports() {
  echo "🔍 Checking UFW firewall status..."

  ufw_status=$(sudo ufw status | head -1)
  if [[ "$ufw_status" == "Status: inactive" ]]; then
    echo "ℹ️ UFW firewall is disabled. No ports need opening."
  else
    echo "🛡️ UFW firewall is active. Opening required ports..."

    # Open HTTP ports
    for service in "${services[@]}"; do
      read -r name port jar <<< "$service"
      echo "🔓 Allowing TCP port $port for $name"
      sudo ufw allow "$port/tcp"
    done

    # Open debug ports
    for debug_port in "${debug_services[@]}"; do
      echo "🔓 Allowing TCP debug port $debug_port"
      sudo ufw allow "$debug_port/tcp"
    done

    echo "🔄 Reloading UFW..."
    sudo ufw reload
  fi
}

stop_services() {
  echo "───────────────────────────────────────────────"
  echo "🔪 Stopping all services..."

  for service in "${services[@]}"; do
    read -r name port jar <<< "$service"
    jar_name=$(basename "$jar")

    echo "Stopping $name (JAR: $jar_name)..."
    sudo pkill -f "$jar_name" && echo "✅ Killed $jar_name" || echo "ℹ️ No running process found for $jar_name"

    # Kill any remaining process listening on the service port
    pids=$(sudo lsof -t -i:$port)
    if [ -n "$pids" ]; then
      echo "⚠️ Killing process(es) on port $port: $pids"
      sudo kill -9 $pids
    fi

    # Also kill debug port processes if any
    debug_port=${debug_services[$name]}
    debug_pids=$(sudo lsof -t -i:$debug_port)
    if [ -n "$debug_pids" ]; then
      echo "⚠️ Killing debug port $debug_port processes: $debug_pids"
      sudo kill -9 $debug_pids
    fi
  done

  echo "✅ All services stopped and ports freed."
}

start_services() {
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

    # Wait for service port to be open
    for i in {1..20}; do
      if nc -z localhost $port; then
        echo "✅ $name HTTP port $port is up."
        break
      fi
      sleep 2
    done

    # Wait for debug port to be open
    for i in {1..20}; do
      if sudo netstat -tulnp 2>/dev/null | grep -q ":$debug_port "; then
        echo "✅ $name debug port $debug_port is listening."
        break
      fi
      sleep 1
    done

    echo "📄 Logs: tail -f $log_file"
  done

  echo "───────────────────────────────────────────────"
  echo "✅ All services restarted successfully with remote debug enabled."
}

# Run all steps
open_firewall_ports
stop_services
start_services
