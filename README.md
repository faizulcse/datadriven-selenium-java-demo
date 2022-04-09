# datadriven-selenium-java-demo

## How to run test in local PC

- Prerequisites
    - Run **Selenium-server** before executing the test
    - How to run selenium
        - Go to project root dir
        - Type command `cd driver/` press **enter**
        - Type command `java -jar selenium-server-4.1.2.jar standalone` press **enter**
        - Type command `ctrl+c` to stop **Selenium-server**
    - **NB**: Don't close the terminal until tests are running
- Run test
    - Go to project root dir
    - Type command `mvn clean test -q` [This will execute all the tests with default browser]
    - Type command `mvn clean test -Dbrowser=firefox -q` [This will execute all the tests with firefox]

## How to run test in Docker container

- Run Docker-server
    - Go to project root dir
    - Open terminal/cmd
    - Build image: `docker build -t myimage .`
    - Run selenium-server: `docker-compose -p hubnetwork up -d --remove-orphans`
    - Run docker
      container: `docker run -t --network=hubnetwork_default -e DOCKER=true myimage mvn clean test -Dbrowser=chrome -q`
    - Stop selenium-server: `docker-compose -p hubnetwork down --remove-orphans`

