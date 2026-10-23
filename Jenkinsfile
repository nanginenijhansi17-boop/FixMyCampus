
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'FixMyCampus source code checked out from GitHub'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn -version'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t jaanu1317/fixmycampus:latest .'
            }
        }
    }
}
