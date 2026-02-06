pipeline {
  agent any
  
  parameters {
    string(name: 'SRC_DIR', defoultValue: 'source', description: 'Источник(директория)')
    string(name: 'DST_DIR', defoultValue: 'target', description: 'Назначение(директория)')
    string(name: 'FILE_NAME', defoultValue: 'myFile.txt', description: 'Имя файла с расширением')
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

            stamp=$(date +%Y-%m-%d)
            src=$(SRC_DIR)/$(FILE_NAME)
            dst=$(DST_DIR)/$(FILE_NAME)

            echo "NEW" > "$src"

            if [ -f "$dst" ] ; then
                base=$()
                ext=$()
            fi
            

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