pipeline {
    agent any
    
    environment{
        dockercredentials=credentials('dockerhubid') 
    }
    stages {
        stage('Build & Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        
        stage('Unit test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('stop and delete last version') {
            steps {
                sh 'docker compose down'   
                //sh 'docker rmi zencaisse || echo "success : image delete"'
                sh 'docker rmi docker rmi $(docker images | grep zencaisse) --force || echo "success : image delete"'
            }
        }

         stage('Build image') {
             steps {
                 sh 'docker build -t zencaisse:$BUILD_NUMBER . '
             }
         }

        // stage('Push image') {
        //     steps {
        //         sh 'echo $dockercredentials_PSW | docker login -u $dockercredentials_USR --password-stdin '
        //         sh 'docker image tag zencaisse:$BUILD_NUMBER lugar2020/zencaisse:$BUILD_NUMBER'
        //         sh 'docker image push lugar2020/zencaisse:$BUILD_NUMBER'
        //     }
        // }
        

        stage('Local deployment') {
            steps {
                sh ''' final_tag=$(echo $BUILD_NUMBER | tr -d ' ') 
                     sed -i "s/docker_tag/$final_tag/g" docker-compose.yml
                    '''
                sh 'docker compose up -d'   
            }
        }
    }
}
