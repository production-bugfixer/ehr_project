pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = tool 'Maven_3.9.5'  // Use the name configured in Jenkins global tools
    }

    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/production-bugfixer/ehr_project.git', branch: 'main'
            }
        }

        stage('Build with Maven') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
            }
        }

        stage('Run Spring Boot App') {
            steps {
                dir('patient') {
                    sh 'java -jar target/*.jar'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed.'
        }
    }
}
