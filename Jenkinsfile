
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }
    }

    post {
        success {
            echo 'FixMyCampus Pipeline completed successfully!'
        }
        failure {
            echo 'FixMyCampus Pipeline failed. Check the console output.'
        }
    }
}
