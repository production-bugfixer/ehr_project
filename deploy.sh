#!/bin/bash

echo "🚀 Starting all EHR services sequentially..."

# Function to start a service after killing any existing process on the port
function start_service() {
  local name=$1
  local port=$2
  local jar_path=$3
  local log_file=$4

  echo "───────────────────────────────────────────────"
  echo "▶️ Checking if port $port is in use for $name..."

  # Kill existing process using the port
  pid=$(lsof -t -i:$port)
  if [ -n "$pid" ]; then
    echo "⚠️ Port $port is in use by PID $pid. Killing it..."
    kill -9 $pid
    sleep 2
  else
    echo "✅ Port $port is free."
  fi

  echo "▶️ Starting $name on port $port..."
  nohup java -jar "$jar_path" --server.port=$port > "$log_file" 2>&1 &

  echo "⏳ Waiting for $name to start on port $port..."
  for i in {1..20}; do
    if nc -z localhost $port; then
      echo "✅ $name is running on port $port."
      return
    fi
    sleep 2
  done

  echo "❌ $name failed to start on port $port after waiting."
}

# List of all EHR services
start_service "registerservice" 8761 "/var/lib/jenkins/workspace/spring-boot-pipeline/registerservice/target/registerservice-0.0.1-SNAPSHOT.jar" "registerservice.log"
start_service "authenticate" 8082 "/var/lib/jenkins/workspace/spring-boot-pipeline/authenticate/target/authenticate-0.0.1-SNAPSHOT.jar" "authenticate.log"
start_service "bloodreport" 8083 "/var/lib/jenkins/workspace/spring-boot-pipeline/bloodreport/target/bloodreport-1.0.0.jar" "bloodreport.log"
start_service "doctor" 8084 "/var/lib/jenkins/workspace/spring-boot-pipeline/doctor/target/doctor-0.0.1-SNAPSHOT.jar" "doctor.log"
start_service "hospitalgateway" 8085 "/var/lib/jenkins/workspace/spring-boot-pipeline/hospitalgateway/target/hospitalgateway-0.0.1-SNAPSHOT.jar" "hospitalgateway.log"
start_service "microbiology" 8086 "/var/lib/jenkins/workspace/spring-boot-pipeline/microbiology/target/microbiology-1.0.0.jar" "microbiology.log"
start_service "patient" 8087 "/var/lib/jenkins/workspace/spring-boot-pipeline/patient/target/patient-0.0.1-SNAPSHOT.jar" "patient.log"

echo "✅ All services processed."
