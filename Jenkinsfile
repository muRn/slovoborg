pipeline {
    agent any
    stages {
        stage('test') {
            steps {
                sh 'gradle test'
            }
        }
        stage('build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}