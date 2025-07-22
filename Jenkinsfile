pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = '/opt/maven'  // You manually installed Maven here
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

        stage('Archive JARs') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
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
