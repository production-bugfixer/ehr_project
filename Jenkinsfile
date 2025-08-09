pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = '/opt/maven'
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

        stage('Collect JARs into build-app') {
            steps {
                script {
                    sh '''
                        echo "📦 Collecting JARs into build-app folder..."
                        mkdir -p build-app

                        cp registerservice/target/registerservice-0.0.1-SNAPSHOT.jar build-app/
                        cp authenticate/target/authenticate-0.0.1-SNAPSHOT.jar build-app/
                        cp bloodreport/target/bloodreport-1.0.0.jar build-app/
                        cp doctor/target/doctor-0.0.1-SNAPSHOT.jar build-app/
                        cp hospitalgatway/target/hospitalgateway-0.0.1-SNAPSHOT.jar build-app/
                        cp microbiology/target/microbiology-1.0.0.jar build-app/
                        cp patient/target/patient-0.0.1-SNAPSHOT.jar build-app/
                    '''
                }
            }
        }
    }

    post {
        always {
            echo '🧹 Post-build steps complete.'
        }

        success {
            echo '✅ Pipeline completed successfully!'
        }

        failure {
            echo '❌ Pipeline failed.'
        }
    }
}
