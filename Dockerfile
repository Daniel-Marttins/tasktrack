# Etapa de build
FROM ubuntu:latest AS build

# Atualizar e instalar dependências
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    wget \
    unzip

# Instalar Maven manualmente (versão 3.8.6 como exemplo)
RUN wget https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz \
    && tar xzvf apache-maven-3.8.6-bin.tar.gz \
    && mv apache-maven-3.8.6 /usr/local/apache-maven \
    && ln -s /usr/local/apache-maven/bin/mvn /usr/bin/mvn

# Definir variável de ambiente do Maven
ENV MAVEN_HOME /usr/local/apache-maven

# Copiar o código-fonte para o container
COPY . /usr/src/app
WORKDIR /usr/src/app

# Rodar o Maven para buildar o projeto (opcional: -DskipTests para pular os testes)
RUN mvn clean install -DskipTests

# Etapa final
FROM openjdk:21-jdk-slim

# Expor a porta 8080
EXPOSE 8080

# Copiar o JAR gerado da etapa de build
COPY --from=build /usr/src/app/target/tasktrack-0.0.1-SNAPSHOT.jar /app.jar

# Definir o entrypoint para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
