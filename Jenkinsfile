pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = '/opt/maven'
        IMAGE_REGISTRY = 'yourdockerhubusername'  // Change this to your actual DockerHub username
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/production-bugfixer/ehr_project.git', branch: 'main'
            }
        }

        stage('Build All Modules') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
            }
        }

        stage('Build & Dockerize All Services') {
            steps {
                script {
                    def services = ['registerservice', 'doctorservice', 'userservice'] // Add more services as needed

                    for (service in services) {
                        dir("${service}") {
                            echo "⚙️ Building Docker image for ${service}"

                            // Check if Dockerfile.template exists and copy it
                            if (fileExists('Dockerfile.template')) {
                                sh 'cp Dockerfile.template Dockerfile'
                            } else {
                                error "🚫 Dockerfile.template not found in ${service}"
                            }

                            // Build Docker image
                            sh """
                                docker build -t ${IMAGE_REGISTRY}/${service}:latest .
                            """

                            // Optional: Push to DockerHub (uncomment below if login is handled)
                            // sh "docker push ${IMAGE_REGISTRY}/${service}:latest"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning Docker resources...'
            sh 'docker system prune -f || true'
        }

        success {
            echo '✅ Pipeline completed successfully!'
        }

        failure {
            echo '❌ Pipeline failed.'
        }
    }
}
