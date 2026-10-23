pipeline {
    agent any

    environment {
        PATH = "C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;${env.PATH}"
        DOCKER_IMAGE = "jaanu1317/fixmycampus:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code checked out from GitHub'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker.exe build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    bat '@echo off\r\necho %DOCKER_PASS%| docker.exe login --username %DOCKER_USER% --password-stdin'
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                bat 'docker.exe push %DOCKER_IMAGE%'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl apply -f k8s/deployment.yaml'
                bat 'kubectl set image deployment/fixmycampus fixmycampus=%DOCKER_IMAGE%'
                bat 'kubectl rollout status deployment/fixmycampus --timeout=120s'
            }
        }

        stage('Docker Logout') {
            steps {
                bat 'docker.exe logout'
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: FixMyCampus built, pushed, and deployed!'
        }
        failure {
            echo 'FAILURE: Check Console Output for the error.'
        }
        always {
            echo 'FixMyCampus pipeline completed.'
        }
    }
}
