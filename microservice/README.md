# Comexport


##Requisitos

```
JDK 1.8
```

##Testes

```
gradlew test
```

## Inicie o docker

```
Caso utilize o boot2docker inicie a VM
```

## Crie a imagem da aplicação

```
gradlew build docker
```

## Execute a imagem utilizando tag local da imagem gerada

```
docker run -p 8080:8080 -t comexport/conta-contabil
```

## Acesse a API no navegador web.

IMPORTANTE: Caso esteja utilizando boot2docker veja qual o IP da VM e utilize o http://${IP}:8080/ para acessar

```
http://localhost:8080/

O arquivo comexport.postman_collection.json contém as rotas de teste do Postman
```

## Para rodar a aplicação sem utilização do docker

```
gradlew build && java -jar build/libs/conta-contabil-0.1.0.jar
```

## Para rodar multiplos containers rode o comando abaixo 

Portas reservadas 8080-8089

```
docker-compose up --scale comexport=5
```

## Execute o commando abaixo para listar os containers

```
docker-compose ps
```

## IMPORTANTE: Para que o load balancer fique acessível seria necessário a configuração completa do HAproxy através do arquivo haproxy.cfg (https://docs.docker.com/samples/library/haproxy/)
