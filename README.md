# CaminhoLivre — API REST

O **CaminhoLivre** é uma plataforma de acessibilidade urbana que busca facilitar a mobilidade de pessoas com deficiência, idosos e pessoas com mobilidade reduzida.

A solução permite o cadastro e a consulta de locais acessíveis, o registro de denúncias sobre problemas de acessibilidade e o acompanhamento dessas informações por cidadãos e gestores.

Este repositório contém o **backend da aplicação**, desenvolvido em Java com Spring Boot.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security com autenticação JWT
- Spring AI
- PostgreSQL
- RabbitMQ
- Maven
- Swagger / OpenAPI
- Docker
- Azure CLI
- Azure DevOps e Azure Pipelines

## Funcionalidades

- Cadastro e autenticação de usuários.
- Controle de acesso por papéis: cidadão e gestor.
- Cadastro, consulta, atualização e exclusão de recursos da API.
- Gerenciamento de locais acessíveis.
- Registro e acompanhamento de denúncias de acessibilidade.
- Persistência de dados no PostgreSQL.
- Comunicação assíncrona com RabbitMQ.
- Integração com inteligência artificial.
- Documentação interativa dos endpoints pelo Swagger.

## Pré-requisitos

Antes de executar o projeto, instale:

- **JDK 21**
- **Maven**
- **PostgreSQL**
- **RabbitMQ**
- **Git**
- Uma IDE Java, como Eclipse, IntelliJ IDEA ou VS Code.

O Docker é opcional para execução em contêiner.

Confira as instalações:

```bash
java -version
mvn -version
git --version
```

## 1. Clonar o repositório

Abra um terminal e execute:

```bash
git clone https://github.com/SEU_USUARIO/CaminhoLivre.git
```

Acesse a pasta do projeto:

```bash
cd CaminhoLivre
```

Substitua `SEU_USUARIO` pelo nome da sua conta GitHub.

## 2. Configurar o PostgreSQL

Inicie o PostgreSQL e crie um banco de dados:

```sql
CREATE DATABASE caminholivre;
```

Caso o projeto contenha o script `scripts/script-bd.sql`, você poderá utilizá-lo para criar as tabelas:

```bash
psql -U postgres -d caminholivre -f scripts/script-bd.sql
```

Verifique antes se o script está atualizado e corresponde às entidades JPA do projeto.

## 3. Configurar as variáveis de ambiente

A aplicação utiliza variáveis de ambiente para configurar o banco de dados, a autenticação e os serviços externos.

Exemplo de configuração em `src/main/resources/application.properties`:

```properties
spring.application.name=caminholivre

server.port=${SERVER_PORT:8089}

spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/caminholivre}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

O valor `ddl-auto=update` é indicado apenas como exemplo para desenvolvimento local. Em ambientes de produção, utilize scripts de migração ou um mecanismo de versionamento do banco.

Também configure as variáveis exigidas pela implementação de segurança JWT, RabbitMQ e Spring AI.

Exemplo de variáveis no PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/caminholivre"
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="SUA_SENHA"

$env:JWT_SECRET="SUA_CHAVE_SECRETA_FORTE"
```

**Importante:** os nomes das variáveis JWT, RabbitMQ e Spring AI devem corresponder às propriedades utilizadas no código da aplicação.

Nunca publique senhas, tokens ou chaves de API no GitHub.

## 4. Configurar o RabbitMQ

Inicie o RabbitMQ localmente e verifique as configurações de conexão utilizadas pela aplicação.

A porta padrão do protocolo AMQP é `5672`.

Caso utilize a interface de gerenciamento do RabbitMQ, ela geralmente fica disponível em:

```text
http://localhost:15672
```

As credenciais e os nomes das filas devem corresponder à configuração do projeto.

## 5. Executar a aplicação

Na raiz do projeto, execute:

```bash
mvn clean install
```

Após a compilação, inicie a API:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8089
```

A inicialização depende de o PostgreSQL, o RabbitMQ e os demais serviços obrigatórios estarem corretamente configurados.

### Executar pelo Eclipse

1. Abra o Eclipse.
2. Selecione **File → Import**.
3. Escolha **Maven → Existing Maven Projects**.
4. Selecione a pasta do projeto CaminhoLivre.
5. Aguarde o carregamento das dependências.
6. Localize a classe principal do Spring Boot.
7. Clique com o botão direito e selecione **Run As → Java Application**.

## 6. Acessar o Swagger

Com a aplicação em execução, abra:

**Swagger UI:**

http://localhost:8089/swagger-ui/index.html

**Documentação OpenAPI em JSON:**

http://localhost:8089/v3/api-docs

Pelo Swagger, é possível consultar os endpoints e testar as operações disponibilizadas pela API.

Os endpoints protegidos exigem autenticação JWT. Primeiro, obtenha um token utilizando o endpoint de login implementado no projeto.

## 7. Executar os testes

Para executar os testes automatizados:

```bash
mvn test
```

Para compilar, testar e gerar o JAR:

```bash
mvn clean package
```

O arquivo gerado estará na pasta:

```text
target/
```

Os relatórios dos testes JUnit, quando configurados com Maven Surefire, estarão em:

```text
target/surefire-reports/
```

## 8. Executar com Docker

O projeto contém um Dockerfile em:

```text
dockerfiles/Dockerfile
```

Para construir a imagem, execute na raiz do projeto:

```bash
docker build -t caminholivre-api:v1 -f dockerfiles/Dockerfile .
```

Para executar o contêiner:

```bash
docker run --name caminholivre-api -p 8089:8089 caminholivre-api:v1
```

**Atenção:** a execução com Docker também exige configurar as variáveis de ambiente e a conectividade com PostgreSQL, RabbitMQ e outros serviços externos.

Dentro do contêiner, `localhost` se refere ao próprio contêiner, não ao computador que está executando o Docker.

## 9. Publicar o projeto no GitHub

### Passo 1 — Criar um repositório público

Acesse:

https://github.com/new

Preencha:

- **Repository name:** `CaminhoLivre`
- **Description:** API REST de acessibilidade urbana desenvolvida com Java e Spring Boot.
- **Visibility:** Public

Clique em **Create repository**.

Se o projeto já possui arquivos localmente, prefira criar o repositório vazio, sem inicializar README, `.gitignore` ou licença.

### Passo 2 — Verificar os arquivos antes da publicação

Na raiz do projeto, confira se existe um `.gitignore` contendo, pelo menos:

```gitignore
target/
.settings/
.project
.classpath
.metadata/
.env
.env.*
!.env.example
*.log
*.pem
*.key
```

Revise os arquivos de configuração para garantir que nenhuma credencial seja publicada.

Se algum segredo já tiver sido enviado anteriormente para um repositório, remova-o do histórico público antes de publicar e substitua a credencial comprometida.

### Passo 3 — Configurar o Git

Se o projeto ainda não estiver versionado:

```bash
git init
git branch -M main
```

Adicione os arquivos:

```bash
git add .
```

Confira o conteúdo preparado para publicação:

```bash
git status
```

Crie o commit, caso existam alterações:

```bash
git commit -m "docs: preparar publicacao do CaminhoLivre"
```

### Passo 4 — Conectar ao GitHub

Se o projeto já utiliza o Azure Repos como `origin`, mantenha essa conexão e adicione o GitHub como um segundo remoto:

```bash
git remote add github https://github.com/SEU_USUARIO/CaminhoLivre.git
```

Se o remoto `github` já existir, verifique sua URL:

```bash
git remote -v
```

### Passo 5 — Publicar o código

Envie a branch principal:

```bash
git push -u github main
```

Caso o GitHub solicite autenticação, utilize o método de login disponibilizado pelo Git Credential Manager ou uma credencial autorizada pela plataforma.

**Observação:** o GitHub será uma cópia pública do código. O Azure Repos continuará sendo utilizado no fluxo privado de Azure Boards, Pull Requests e Azure Pipelines.

Para manter os dois repositórios atualizados, envie para o GitHub apenas as alterações já aprovadas e integradas à `main` no Azure Repos.

## 10. Integração contínua e entrega contínua

O projeto utiliza Azure DevOps para organizar o desenvolvimento.

### Azure Boards

Gerencia tarefas e vincula alterações do código aos Work Items.

### Azure Repos

Armazena o código em um repositório Git privado, com políticas de revisão e Pull Requests.

### Azure Pipelines — CI

O arquivo `azure-pipelines.yml` define a pipeline responsável por:

- Compilar a API com Maven.
- Executar testes automatizados.
- Publicar os resultados JUnit.
- Gerar e publicar o artefato JAR.

A execução é acionada por alterações na branch `main`.

### Azure Pipelines — CD

O arquivo `azure-pipelines-release.yml` define o processo de entrega contínua, acionado após uma execução bem-sucedida da CI.

A implantação no Azure depende da disponibilidade e da configuração da infraestrutura de hospedagem.

## 11. Estrutura do projeto

```text
CaminhoLivre/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── scripts/
│   ├── provisionar-azure.sh
│   ├── configurar-app.sh
│   └── script-bd.sql
├── dockerfiles/
│   └── Dockerfile
├── azure-pipelines.yml
├── azure-pipelines-release.yml
├── .gitignore
├── pom.xml
└── README.md
```

A estrutura representa a organização prevista do projeto; alguns arquivos podem variar conforme a implementação.

## 12. Objetivo do projeto

O CaminhoLivre busca utilizar tecnologia para melhorar a acessibilidade e a inclusão nas cidades, oferecendo ferramentas que permitam identificar problemas urbanos, compartilhar informações e apoiar a mobilidade de cidadãos.

O backend Java fornece os serviços necessários para integrar essas funcionalidades ao aplicativo mobile e a futuras interfaces de gestão.

---

**Projeto acadêmico — CaminhoLivre**

Desenvolvido com Java, Spring Boot e tecnologias de integração e implantação em nuvem.