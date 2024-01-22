## Event driven project with twitter theme
In this project we are using event driven design in a simulation of consumer of api tweets.

We are using:
* Spring Boot
* Kafka
* Docker
* Config Server
* Project Reactor
* Elastic
* SOLID principles

### Util commands

1. To see if kafka containers are running with kafka cat <br/>
```kafkacat -L -b localhost:19092```
2. Listen twitter-topic with kafkacat<br/>
```kafkacat -C -b localhost:19092 -t twitter-topic```
