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
    
    stage('Groovy Test'){
        steps{
            script {
            def fileName = 'myFile'
            def stamp = '30-01-2026'
            echo "${fileName}_old_${stamp}"
            }
        }
    }
    
    stage('Env test'){
        steps{
            echo "SRC=${env.SRC_DIR}, DST=${env.DST_DIR}"
            sh 'echo "SRC=$SRC_DIR, DST=$DST_DIR"'
        }
    }
    
    stage('Shell test'){
        steps{
            sh '''
            mkdir -p test_dir
            echo "hello from file" > test_dir/test.txt
            cat test_dir/test.txt
            ls -la test_dir
            '''
        }
    }
    
    stage('Rename test') {
        steps{
            sh '''
            mkdir -p target
            echo "OLD" > target/myFile.txt
            stamp=$(date +%Y-%m-%d)

            if [ -f target/myFile.txt ] ; then
                mv target/myFile.txt "target/myFile_old_${stamp}.txt"
            fi

            ls -la target
            '''
        }
    }

    stage('PreFinal') {
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
    
    stage('End') {
        steps{
            echo 'Goodbye Jenkins'
        }
    }
  }
}