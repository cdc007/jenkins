node{
// add from dev
    stage('SCM'){
        echo 'gather git change......'
    }

    stage('Build'){
        echo 'building..........'
    }
    stage('Test'){
        echo 'testing..........'
    }
    stage('Deploy'){
        echo 'deploy...........'
    }

}
