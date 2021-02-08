pipeline {
    agent any
    stages {
        stage('test') {
            steps {
                withGradle 'test'
            }
        }
        stage('build') {
            steps {
                withGradle 'build'
            }
        }
    }
}