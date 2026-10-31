
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
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
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
                        echo %DOCKER_PASS% | "%DOCKER_EXE%" login --username %DOCKER_USER% --password-stdin
                    '''
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
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
            echo 'SUCCESS: FixMyCampus CI/CD pipeline completed!'
        }

        failure {
            echo 'FAILURE: Check Console Output for the error.'
        }

        always {
            echo 'FixMyCampus pipeline execution finished.'
        }
    }
}
