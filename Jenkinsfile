pipeline {
    agent any

    tools {
        jdk 'JDK-21'
        maven 'Maven-3'
    }

    environment {
        PROJECT_NAME = 'projet-cucumber'
        REPORT_DIR   = 'target/cucumber-rapports'
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
                sh 'mvn clean compile -q'
            }
        }

        stage('Tests - Smoke') {
            steps {
                sh 'mvn test -Dcucumber.filter.tags="@smoke"'
            }
        }

        stage('Tests - Regression') {
            steps {
                sh 'mvn test -Dcucumber.filter.tags="@regression"'
            }
        }

        stage('Rapport Cucumber') {
            steps {
                sh 'mvn verify -DskipTests'
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
            echo "Pipeline terminée avec succès — ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
        failure {
            echo "Echec de la pipeline — consulter les logs et le rapport Cucumber"
        }
        always {
            archiveArtifacts artifacts: 'target/cucumber-rapports/**', allowEmptyArchive: true
            cleanWs()
        }
    }
}
