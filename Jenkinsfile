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