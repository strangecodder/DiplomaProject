# Launch instuction
## Description
This project is my diploma work like a those two repositories:
1. Frontend part: https://github.com/strangecodder/DiplomaProjectFront
2. OPC UA servers by C: 
The main aim of this project is realize server what can communicate with a IIoT gateway, that in IIRA architecture and in IIoT conception respond on sensors changes and send data to server

## Описание
Это проект моей дипломной работы как и два этих репозитория:
1. Часть фронтенда: https://github.com/strangecodder/DiplomaProjectFront
2. OPC UA сервера на Си: 
Главной целью работы было реализовать сервер, который взаимодействует с IIoT гейтом, который в архитектуре IIRA и концепции Промышленного интернета вещей отвечает за сбор данных с датчиков
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
