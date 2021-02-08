pipeline {
    agent any
    stages {
        stage('test') {
            steps {
                sh 'pwd'
                sh 'echo $PATH'
            }
        }
        stage('build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}