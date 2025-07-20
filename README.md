# Launch instuction
## 1. Download soft
Firstly, you need download some soft for your system
* JDK (version 17+):
```
sudo apt update
sudo apt install openjdk-17-jdk
```
* Docker
```
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```
* Maven
```
sudo apt update
sudo apt install maven
```
## 2. Launch project
For launch project execute that commands
```
sudo docker-compose up -d
```
```
mvn spring-boot:run
```
