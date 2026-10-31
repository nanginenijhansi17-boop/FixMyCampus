
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'jaanu1317/fixmycampus:latest'
    }

    stages {

        // Stage 1: Checkout
        stage('Checkout') {
            steps {
                echo 'Source code checked out from GitHub'
            }
        }

        // Stage 2: Build with Maven
        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        // Stage 3: Build Docker Image
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        // Stage 4: Login to Docker Hub
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
                        echo %DOCKER_PASS% | docker login --username %DOCKER_USER% --password-stdin
                    '''
                }
            }
        }

        // Stage 5: Push to Docker Hub
        stage('Push to Docker Hub') {
            steps {
                bat 'docker push %DOCKER_IMAGE%'
            }
        }

        // Stage 6: Check Kubernetes Connection
        stage('Check Kubernetes Connection') {
            steps {
                bat 'kubectl cluster-info'
                bat 'kubectl get nodes'
            }
        }

        // Stage 7: Deploy to Kubernetes
        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl apply -f k8s/'
                bat 'kubectl rollout status deployment/fixmycampus'
            }
        }

        // Stage 8: Docker Logout
        stage('Docker Logout') {
            steps {
                bat 'docker logout'
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
