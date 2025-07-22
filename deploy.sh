#!/bin/bash

# Define base path and services
BASE_PATH="/var/lib/jenkins/workspace/spring-boot-pipeline"
SERVICES=("authenticate" "registerservice" "bloodreport" "doctor" "patient" "hospitalgatway" "microbiology")

# Java executable (update if needed)
JAVA_PATH="/usr/bin/java"

# User to run the services as
RUN_USER="jenkins"

# Loop through each service
for SERVICE in "${SERVICES[@]}"
do
    SERVICE_DIR="${BASE_PATH}/${SERVICE}/target"
    JAR_FILE=$(find "$SERVICE_DIR" -name "*.jar" | head -n 1)

    if [ -f "$JAR_FILE" ]; then
        SERVICE_FILE="/etc/systemd/system/${SERVICE}.service"
        echo "Deploying $SERVICE using $JAR_FILE"

        # Create systemd service file
        cat > "$SERVICE_FILE" <<EOL
[Unit]
Description=${SERVICE^} Spring Boot Service
After=network.target

[Service]
User=$RUN_USER
ExecStart=$JAVA_PATH -jar $JAR_FILE
SuccessExitStatus=143
Restart=always
RestartSec=5
Environment=SPRING_PROFILES_ACTIVE=prod

[Install]
WantedBy=multi-user.target
EOL

        # Reload and start service
        systemctl daemon-reexec
        systemctl daemon-reload
        systemctl enable $SERVICE
        systemctl restart $SERVICE
        echo "✅ $SERVICE deployed and restarted."
    else
        echo "❌ JAR not found for $SERVICE in $SERVICE_DIR"
    fi
done
