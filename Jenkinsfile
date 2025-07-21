pipeline {
    agent any

    environment {
        PATH = "/opt/maven/bin:/usr/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/production-bugfixer/ehr_project'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Spring Boot App') {
            steps {
                sh 'java -jar target/*.jar'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
