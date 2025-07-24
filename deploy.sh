#!/bin/bash

echo "🔁 Stopping any running services on ports 8761, 8082–8087..."

# Define a map of services and their ports
declare -A services
services=(
  [registerservice]=8761
  [authenticate]=8082
  [bloodreport]=8083
  [doctor]=8084
  [hospitalgateway]=8085
  [microbiology]=8086
  [patient]=8087
)

# Step 1: Kill any running processes on each port
for service in "${!services[@]}"; do
  port=${services[$service]}
  pid=$(lsof -ti tcp:$port)
  if [ -n "$pid" ]; then
    echo "⚠️ Stopping $service on port $port (PID: $pid)..."
    kill -9 $pid
  else
    echo "✅ Port $port is already free."
  fi
done

# Step 2: Reset UFW rules for ports 8761-8087
echo "🔒 Enforcing firewall policies..."
for port in "${services[@]}"; do
  sudo ufw deny $port
done

# Step 3: Allow ONLY registerservice and authenticate ports publicly
sudo ufw allow 8761 comment "✅ Allow Eureka registry (public)"
sudo ufw allow 8082 comment "✅ Allow Authenticate service (public)"

# Step 4: Start all services
echo "🚀 Starting all EHR services..."
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/registerservice/target/registerservice-0.0.1-SNAPSHOT.jar --server.port=8761 > registerservice.log 2>&1 &
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/authenticate/target/authenticate-0.0.1-SNAPSHOT.jar --server.port=8082 > authenticate.log 2>&1 &
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/bloodreport/target/bloodreport-1.0.0.jar --server.port=8083 > bloodreport.log 2>&1 &
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/doctor/target/doctor-0.0.1-SNAPSHOT.jar --server.port=8084 > doctor.log 2>&1 &
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/hospitalgateway/target/hospitalgateway-0.0.1-SNAPSHOT.jar --server.port=8085 > hospitalgateway.log 2>&1 &
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/microbiology/target/microbiology-1.0.0.jar --server.port=8086 > microbiology.log 2>&1 &
nohup java -jar /var/lib/jenkins/workspace/spring-boot-pipeline/patient/target/patient-0.0.1-SNAPSHOT.jar --server.port=8087 > patient.log 2>&1 &

# Step 5: Output deployment summary
echo "📊 Deployment Summary:"
for service in "${!services[@]}"; do
  port=${services[$service]}
  if [[ "$port" == 8082 || "$port" == 8761 ]]; then
    access="🌐 Public"
  else
    access="🔒 Private"
  fi
  echo " - $service -> Port $port ($access)"
done

echo "✅ All services started. Use 'ps aux | grep java' to verify."
