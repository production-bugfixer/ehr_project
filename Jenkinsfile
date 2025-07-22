pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = '/opt/maven' // Your custom Maven installation
        IMAGE_NAME = 'ehr-service-image'
        DOCKER_REGISTRY = '' // Set this if pushing to a registry (e.g., 'registry.hub.docker.com/username')
    }

    stages {
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

        stage('Archive JARs') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('registerservice') {
                    sh '''
                        cp Dockerfile.template Dockerfile
                        docker build -t $IMAGE_NAME .
                    '''
                }
            }
        }

        stage('Push Docker Image') {
            when {
                expression { return env.DOCKER_REGISTRY?.trim() }
            }
            steps {
                sh '''
                    docker tag $IMAGE_NAME $DOCKER_REGISTRY/$IMAGE_NAME
                    docker push $DOCKER_REGISTRY/$IMAGE_NAME
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh 'sh registerservice/deploy.sh'
            }
        }
    }

    post {
        always {
            echo 'Cleaning workspace...'
            sh 'docker system prune -f'
        }
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed.'
        }
    }
}
