pipeline {
 agent any
 tools {
 maven 'Maven'
 }
 stages {
 stage("Source") {
 steps {
 git branch: 'main', url: 'https://github.com/mvffUnderCover/repo-tp-seydi-cisse.git'
 }
 }
 stage("Build") {
 steps {
 bat 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
 }
 }
 stage("SonarQube Analysis") {
 steps {
 bat 'mvn sonar:sonar'
 }
 }
 stage('Approve Deployment') {
 input {
 message "Do you want to proceed for deployment?"
 }
 steps {
 bat 'echo "Deploying into Server"'
 }
 }
 }
 post {
 aborted {
 echo "Sending message to agent"
 }
 success {
         emailext(
           subject: "Build Successful: ${CurrentBuild.fullDisplayName}",
           body: "The build was successful. No further action is required. ",
           recipientProviders: [culprits(), Developers()]
           replyTo:"cmakhtar497@gmail.com",
           to:"seydi3369@gmail.com"
         )
  }
 failure {
       emailext(
                  subject: "Build Successful: ${CurrentBuild.fullDisplayName}",
                             body: "The build was failed. No further action is required. ",
                             recipientProviders: [culprits(), Developers()]
                             replyTo:"cmakhtar497@gmail.com",
                             to:"seydi3369@gmail.com"
                )
 }

 }
}
