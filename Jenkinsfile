
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

        // Stage 2: Build the application using Maven
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
    }

    // Pipeline result messages
    post {
        success {
            echo 'SUCCESS: FixMyCampus CI/CD Pipeline completed!'
        }

        failure {
            echo 'FAILURE: Pipeline failed. Check Console Output.'
        }

        always {
            echo 'FixMyCampus Pipeline execution finished.'
        }
    }
}
