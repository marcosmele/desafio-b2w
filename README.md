## DESAFIO B2W

Este projeto consiste de uma API de planetas de star wars desenvolvida para o desafio b2w.

###Tecnologias

Esta API Rest está desenvolvida em Java em sua versão <a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">11</a> com <a href="https://spring.io/projects/spring-boot">Spring Boot 2</a>, utilizando <a href="https://www.mongodb.com/">MongoDB</a> como repositório de dados

###Instalação

[![Docker Logo](https://d207aa93qlcgug.cloudfront.net/1.95.5.qa/img/nav/docker-logo-loggedout.png)](https://hub.docker.com/r/marcosmele/starwars-api/)

Utilize o arquivo docker-compose.yml
`docker-compose up --build`


###Documentação

Funcionalidades:

* Inserir Planeta
* Listar Planetas
* Consultar planeta por nome
* Consultar planeta por identificador
* Deletar planeta

A documentação da API pode ser melhor visualizada de forma iterativa através do <a href="http://localhost:8080/starwars/swagger-ui.html">Swagger</a> assim que a aplicação estiver no ar.

###Desenvolvimento

O projeto foi desenvolvido na IDE <a href="https://www.eclipse.org/">Eclipse</a> utilizando o padrão Maven para gerenciamento de dependências.
