pipeline {
  agent any
  stages {
    stage('clone') {
      steps {
        git(url: 'https://github.com/ramyaranjan1992/split', branch: 'master', poll: true)
      }
    }

  }
}