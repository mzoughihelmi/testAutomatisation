pipeline {
    agent any

    tools {
        jdk 'JDK-21'
        maven 'Maven3'
    }

    environment {
        PROJECT_NAME = 'projet-cucumber'
        REPORT_DIR   = 'target/cucumber-reports'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile -q'
            }
        }

        stage('Tests - Smoke') {
            steps {
                bat 'mvn test -Dcucumber.filter.tags="@smoke"'
            }
        }

        stage('Rapport Cucumber') {
            steps {
                bat 'mvn verify -DskipTests'
            }
            post {
                always {
                    publishHTML(target: [
                        allowMissing         : false,
                        alwaysLinkToLastBuild: true,
                        keepAll              : true,
                        reportDir            : "${REPORT_DIR}",
                        reportFiles          : 'rapport.html',
                        reportName           : 'Cucumber Report'
                    ])
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline terminée — ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
        failure {
            echo "❌ Echec — consulter les logs et le rapport Cucumber"
        }
        always {
            archiveArtifacts artifacts: 'target/cucumber-reports/**',
                             allowEmptyArchive: true
            cleanWs()
        }
    }
}