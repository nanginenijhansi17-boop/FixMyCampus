
stage('Login to Docker Hub') {
    steps {
        withCredentials([
            usernamePassword(
                credentialsId: 'dockerhub-new',
                usernameVariable: 'DOCKER_USER',
                passwordVariable: 'DOCKER_PASS'
            )
        ]) {
            bat '''
                @echo off
                docker.exe logout
                echo %DOCKER_PASS% | docker.exe login --username %DOCKER_USER% --password-stdin
            '''
        }
    }
}
