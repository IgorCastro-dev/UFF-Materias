
# Uma Api em Java do projeto UFF-Materias

Uma Api Rest em java para gerenciar arquivos do curso de sistemas de informação da UFF, contendo gerenciamento de matérias, de tópicos, de conteúdo, cadastro de usuário, login de usuário, sistema de autenticação, envio de email, recuperação de senha e muito mais. 


![Logo](https://github.com/IgorCastro-dev/UFF-Materias/assets/77001554/b9569d56-a018-4a8e-9514-8a051852f87d)

## Frontend da API
https://github.com/IgorCastro-dev/UFF-Mat-rias

## Stack utilizada

**Back-end:** Java, Spring-Boot,JPA, My-SQL, Redis, AWS, Swagger, Docker, SMTP, flyway, Segurança (JWT Oauth2),Domain Events, DDD


## Funcionalidades

- CRUD de dados
- Autenticação com jwt
- envio de arquivos(fotos,pdfs,vídeos,...)
- Armazenamento na nuvem da AWS
- Envio de eventos
- envio de email via smtp
- atualização de senha
- login de usuários


## Rodando localmente

Entre no terminal

Clone o projeto

```bash
  git clone https://github.com/IgorCastro-dev/UFF-Materias.git
```

Entre no diretório do projeto

```bash
  cd UFF-Materias
```

Execute o Docker Compose:

```bash
  docker-compose up -d
```

Compile e Execute sua API Java:

```bash
  ./mvnw spring-boot:run
```


## Screenshots

![Swagger](https://github.com/IgorCastro-dev/UFF-Materias/assets/77001554/55a4baca-5921-452c-a711-09fa2ff75e15)

![Swagger](https://github.com/IgorCastro-dev/UFF-Materias/assets/77001554/818ad148-17b6-4f14-8269-39ba26fd6d1c)

![Swagger](https://github.com/IgorCastro-dev/UFF-Materias/assets/77001554/44ff81be-86b8-480b-adb0-edb85a04b1da)

![Swagger](https://github.com/IgorCastro-dev/UFF-Materias/assets/77001554/ca8cc27a-4c50-4c56-b945-55ad71e08cf4)

![Swagger](https://github.com/IgorCastro-dev/UFF-Materias/assets/77001554/a0ff905d-43b8-46da-9175-0145db47d29f)

## Documentação

[Documentação](http://localhost:8080/v1/api/uff-materias/swagger-ui/index.html#/)
