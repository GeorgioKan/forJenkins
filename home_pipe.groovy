pipeline {
  agent any
  
  environment{
      SRC_DIR = 'source'
      DST_DIR = 'target'
  }
  stages {
      
    stage('Init') {
      steps {
        echo 'Hello Jenkins'
      }
    }

    stage ('Checkout'){
        steps{
           checkout scm          
        }
    }

    stage('MoveAndRename') {
        steps{
            sh '''
            mkdir -p "${SRC_DIR}" "${DST_DIR}"
            echo "NEW" > "${SRC_DIR}/myFile.txt"
            stamp=$(date +%Y-%m-%d)

            if [ -f "${DST_DIR}/myFile.txt" ] ; then
                mv "${DST_DIR}/myFile.txt" "${DST_DIR}/myFile_old_${stamp}.txt"
            fi
            
            mv "${SRC_DIR}/myFile.txt" "${DST_DIR}/myFile.txt"

            ls -la "${SRC_DIR}"
            ls -la "${DST_DIR}"
            '''
        }
    }
    stage ('Git history'){
        steps{
            sh 'git log -5'
        }
    }
    stage('End') {
        steps{
            echo 'Goodbye Jenkins'
        }
    }
  }
}