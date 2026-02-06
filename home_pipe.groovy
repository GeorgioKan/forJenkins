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
    
    stage('End') {
        steps{
            echo 'Goodbye Jenkins'
        }
    }
  }
}