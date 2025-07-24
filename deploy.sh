#!/bin/bash

# Base directory where all modules are located
BASE_DIR="/var/lib/jenkins/workspace/spring-boot-pipeline"

# Define your module list here
MODULES=("registerservice" "doctor" "patient" "authenticate" "bloodreport" "hospitalgateway" "microbiology")

# Kill previous processes (optional but clean)
echo "Stopping old services if any..."
for MODULE in "${MODULES[@]}"; do
    pkill -f "$MODULE-0.0.1-SNAPSHOT.jar"
done

# Start each service
echo "Starting all services..."
for MODULE in "${MODULES[@]}"; do
    JAR_PATH="$BASE_DIR/$MODULE/target/$MODULE-0.0.1-SNAPSHOT.jar"

    if [[ -f "$JAR_PATH" ]]; then
        echo "Launching $MODULE..."
        nohup java -jar "$JAR_PATH" > "$BASE_DIR/$MODULE/nohup.out" 2>&1 &
    else
        echo "❌ JAR not found for $MODULE"
    fi
done

echo "✅ All available services launched."
