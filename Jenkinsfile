pipeline {
    agent any

    tools {
        maven 'Maven 3.9.5' // Set the name you configured in Jenkins → Global Tool Configuration
        jdk 'Java 17'       // Set this to match the JDK name in Jenkins
    }

    environment {
        JAR_NAME = "target/ehr_project-0.0.1-SNAPSHOT.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/production-bugfixer/ehr_project', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Run Spring Boot App') {
            steps {
                sh 'nohup java -jar $JAR_NAME &'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build and run successful.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
