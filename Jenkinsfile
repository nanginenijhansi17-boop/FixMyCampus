
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code will be checked out from GitHub.'
            }
        }

        stage('Build') {
            steps {
                echo 'Building FixMyCampus with Maven'
            }
        }

        stage('Docker') {
            steps {
                echo 'Preparing the FixMyCampus Docker image'
            }
        }
    }
}
