pipeline {
    agent any

    tools {
        gradle "default"
    }

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