# Use a imagem do OpenJDK 21
FROM openjdk:21-jdk-slim

# Diretório de trabalho
WORKDIR /app

# Copiar o projeto
COPY . .

# Tornar o mvnw executável e executar o Maven
RUN chmod +x ./mvnw && ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
