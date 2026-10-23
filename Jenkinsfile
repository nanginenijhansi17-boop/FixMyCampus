
pipeline {
    agent any

    environment {
        PATH = "C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;${env.PATH}"
    }

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
                bat 'docker.exe build -t jaanu1317/fixmycampus:latest .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat 'docker.exe login -u %DOCKER_USER% -p %DOCKER_PASS%'
                    bat 'docker.exe push jaanu1317/fixmycampus:latest'
                    bat 'docker.exe logout'
                }
            }
        }
    }
}
