
# ==========================================
# CaminhoLivre - API Java Spring Boot
# Dockerfile com Java 21
# ==========================================

# ETAPA 1 - Compilacao com Maven
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar o arquivo de dependencias
COPY pom.xml .

# Baixar dependencias Maven
RUN mvn dependency:go-offline -B

# Copiar o codigo-fonte
COPY src ./src

# Compilar e gerar o arquivo JAR
RUN mvn clean package -B

# ETAPA 2 - Execucao da aplicacao
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Porta interna utilizada pela API
EXPOSE 8089

# Iniciar a aplicacao Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
