pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = '/opt/maven'
        IMAGE_REGISTRY = 'animsamanta' // Not used anymore, but you can delete it if not needed
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Clone Repository') {
            steps {
                git credentialsId: 'github-creds', url: 'https://github.com/production-bugfixer/ehr_project.git', branch: 'main'
            }
        }

        stage('Build All Modules') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
            }
        }

        stage('Trigger Deploy Job') {
            steps {
                echo "🚀 Triggering deployment after build"
                build job: 'ehr-back-end-deploy'
            }
        }
    }

    post {
        always {
            echo '🧹 Post-build steps complete.'
            // Docker cleanup removed
        }

        success {
            echo '✅ Pipeline completed successfully!'
        }

        failure {
            echo '❌ Pipeline failed.'
        }
    }
}
