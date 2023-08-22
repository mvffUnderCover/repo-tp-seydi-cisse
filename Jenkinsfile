pipeline {
    agent any

    tools {
        maven "3.6.3"
    }

    stages {
        stage('Example') {
            steps {
                sh "mvn --version"
            }
        }
    }
}
