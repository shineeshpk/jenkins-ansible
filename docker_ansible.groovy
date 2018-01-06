node {
    def repo="${GITHUB_REPO}"
    try{
        stage('chechout'){
            git "${repo}"
        }
        stage('install pip and docker-py'){
            ansiblePlaybook credentialsId: 'd1a36cbc-7af0-4ab8-814f-3cad686ef9ab', 
                            inventory: 'hosts', 
                            playbook: 'prerequisite.yml', 
                            sudo: true
        }
        stage('execute playbook'){
            ansiblePlaybook credentialsId: 'd1a36cbc-7af0-4ab8-814f-3cad686ef9ab', 
                            inventory: 'hosts', 
                            playbook: 'docker.yml', 
                            sudo: true
        }
        stage('docker launched'){
            print "Docker container launched."
        }
        stage('success mail'){
            mail body: "Execution of Jenkins pipeline successful.",
             to: 'franklinsijo@gmail.com',
             cc: '',
             bcc: '', 
             from: 'jenkins-master@jenkins.io',
             replyTo: '',
             subject: 'Success: Ansible Playbook pipeline'
        }
    }
    catch (err){
        stage('failure mail'){
            mail body: "Execution of Jenkins pipeline failed.",
             to: 'franklinsijo@gmail.com',
             cc: '',
             bcc: '', 
             from: 'jenkins-master@jenkins.io',
             replyTo: '',
             subject: 'Failed: Ansible Playbook pipeline'
        }    
    }
}