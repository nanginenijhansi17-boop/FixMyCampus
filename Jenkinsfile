
pipeline {
    agent any

    stages {

        // Stage 1: Checkout source code from GitHub
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Source code downloaded successfully!'
            }
        }

        // Stage 2: Build application using Maven
        stage('Maven Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
                echo 'Maven build completed successfully!'
            }
        }

        // Stage 3: Run application tests
        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
                echo 'All tests completed successfully!'
            }
        }

        // Stage 4: Build Docker image
        stage('Docker Build') {
            steps {
                bat '"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" build -t fixmycampus:1.0 .'
                echo 'Docker image built successfully!'
            }
        }

        // Stage 5: Login to Docker Hub and push image
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
@echo off

set "DOCKER_CONFIG=%WORKSPACE%\\.docker-config"

if not exist "%DOCKER_CONFIG%" mkdir "%DOCKER_CONFIG%"

echo {"auths":{}} > "%DOCKER_CONFIG%\\config.json"

powershell.exe -NoProfile -NonInteractive -Command "$env:DOCKERHUB_TOKEN | & 'C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe' login --username $env:DOCKERHUB_USER --password-stdin"

if errorlevel 1 goto failed

"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" tag fixmycampus:1.0 %DOCKERHUB_USER%/fixmycampus:1.0

if errorlevel 1 goto failed

"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" push %DOCKERHUB_USER%/fixmycampus:1.0

if errorlevel 1 goto failed

rmdir /s /q "%DOCKER_CONFIG%"
exit /b 0

:failed
rmdir /s /q "%DOCKER_CONFIG%"
exit /b 1
'''
                }
            }
        }
    }

    // Post-build actions
    post {

        success {
            echo 'SUCCESS: FixMyCampus CI/CD pipeline completed!'
            echo 'Docker image pushed to Docker Hub successfully!'
        }

        failure {
            echo 'FAILURE: Pipeline execution failed.'
            echo 'Please check Jenkins Console Output.'
        }

        always {
            echo 'FixMyCampus pipeline execution finished.'
        }
    }
}
