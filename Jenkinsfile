
pipeline {
    agent any

    stages {

        // STAGE 1: CHECKOUT FROM GITHUB
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Source code downloaded successfully!'
            }
        }

        // STAGE 2: MAVEN BUILD
        stage('Maven Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
                echo 'Maven build completed successfully!'
            }
        }

        // STAGE 3: RUN TEST CASES
        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
                echo 'All tests completed successfully!'
            }
        }

        // STAGE 4: BUILD DOCKER IMAGE
        stage('Docker Build') {
            steps {
                bat '''
"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" build -t fixmycampus:1.0 .
'''
                echo 'Docker image built successfully!'
            }
        }

        // STAGE 5: DOCKER HUB LOGIN AND PUSH
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

        // STAGE 6: KUBERNETES DEPLOYMENT
        stage('Kubernetes Deployment') {
            steps {
                bat '''
"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\kubectl.exe" --kubeconfig="C:\\ProgramData\\Jenkins\\.kube\\config" apply -f k8s/deployment.yaml

if errorlevel 1 exit /b 1

"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\kubectl.exe" --kubeconfig="C:\\ProgramData\\Jenkins\\.kube\\config" apply -f k8s/service.yaml

if errorlevel 1 exit /b 1

"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\kubectl.exe" --kubeconfig="C:\\ProgramData\\Jenkins\\.kube\\config" rollout status deployment/fixmycampus-deployment --timeout=180s

if errorlevel 1 exit /b 1

"C:\\Users\\mouni\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\kubectl.exe" --kubeconfig="C:\\ProgramData\\Jenkins\\.kube\\config" get pods
'''
                echo 'Kubernetes deployment completed successfully!'
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: FixMyCampus CI/CD pipeline completed!'
            echo 'Docker image pushed to Docker Hub!'
            echo 'Application deployed to Kubernetes!'
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
