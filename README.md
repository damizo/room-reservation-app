
  
**Recruitment task for Cosmose.**     
 **Spring Boot application backed by MariaDB in Docker containers**     
 Setting up environment:    
    
**1.** MariaDB must be pulled from default DockerHub registry, so first step is to log in to DockerHub registry as below.    
    
*docker login*     
 (you will be asked for enter credentials)    
    
    
**2**. Now you're able to up containers. There is a two containers listen on different ports, one container has to run for integration tests, another one is reserved for Spring Boot application.    
    
*docker-compose up -d mariadb_test* 
docker-compose logs -f mariadb_test

docker-compose up -d mariadb_main
docker-compose logs -f mariadb_test
   
 Docker will download MariaDB image from registry and will up him in mariadb_test and mariadb containers.      
    
**3.** Maven build with integration tests included.    
    
*mvn clean install*    
    
 **4.** Start Spring Boot application.    
    
*mvn spring-boot:run*  
  
  
**5.** Invoke endpoints in order:  
**search** -> **register customer** -> **reserve room** -> again check **search** whether room disappeared in results  
**check room** -> **cancel reservation** -> now room with first criteria should be searchable in **search** endpoint  
  
Remarks:  
- SQL initialization scripts runs with application  
- Folder **examples** contains postman collection with all endpoints (I recommend to invoke them in order).
- If there is a create scheme issues, try to remove all images and containers related with MariaDB:
	 **1.** docker rmi -f mariadb*
	 **2.** docker rm $(docker ps -a | grep mariadb)*
	 **3.** run containers just like in step **2**
- Swagger documentation:  **localhost:8189/swagger-ui.html**