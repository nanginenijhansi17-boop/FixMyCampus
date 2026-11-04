
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'jaanu1317/fixmycampus:latest'
        DOCKER_EXE = 'C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Source code checked out from GitHub'
            }
        }

        stage('Build with Maven') {
            steps {
                echo 'Building FixMyCampus'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Creating Docker image'
                bat '"%DOCKER_EXE%" build -t %DOCKER_IMAGE% .'
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
                    bat '''
                        @echo off
                        powershell -NoProfile -NonInteractive -Command "[System.IO.File]::WriteAllText((Join-Path $env:TEMP 'docker-pat.txt'), $env:DOCKER_PASS)"

                        if errorlevel 1 exit /b 1

                        "%DOCKER_EXE%" login --username "%DOCKER_USER%" --password-stdin < "%TEMP%\\docker-pat.txt"

                        if errorlevel 1 (
                            del "%TEMP%\\docker-pat.txt"
                            exit /b 1
                        )

                        del "%TEMP%\\docker-pat.txt"
                    '''
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                echo 'Pushing image to Docker Hub'
                bat '"%DOCKER_EXE%" push %DOCKER_IMAGE%'
            }
        }

        stage('Check Kubernetes Connection') {
            steps {
                bat 'kubectl cluster-info'
                bat 'kubectl get nodes'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl apply -f k8s/'
                bat 'kubectl rollout status deployment/fixmycampus'
            }
        }

        stage('Docker Logout') {
            steps {
                bat '"%DOCKER_EXE%" logout'
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: FixMyCampus CI/CD Pipeline Completed!'
        }

        failure {
            echo 'FAILURE: Check Console Output for the error.'
        }

        always {
            echo 'FixMyCampus pipeline execution finished.'
        }
    }
}
