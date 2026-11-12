
pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                echo 'Source code downloaded successfully!'
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
                echo 'Maven build completed!'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
                echo 'Tests completed!'
            }
        }

        stage('Docker Build') {
            steps {
                bat '"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" build -t fixmycampus:1.0 .'
                echo 'Docker image built!'
            }
        }

        stage('Docker Hub Login and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKERHUB_USER',
                        passwordVariable: 'DOCKERHUB_TOKEN'
                    )
                ]) {
                    bat '''
                    echo %DOCKERHUB_TOKEN% | "C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" login --username %DOCKERHUB_USER% --password-stdin

                    "C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" tag fixmycampus:1.0 %DOCKERHUB_USER%/fixmycampus:1.0

                    "C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" push %DOCKERHUB_USER%/fixmycampus:1.0
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: FixMyCampus image pushed to Docker Hub!'
        }

        failure {
            echo 'Pipeline failed. Check Console Output.'
        }

        always {
            echo 'FixMyCampus pipeline execution finished.'
        }
    }
}
