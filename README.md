# Eco-Report

API REST para registro e consulta de ocorrências ambientais, desenvolvida como projeto acadêmico da disciplina de Backend da SATC.

## Problema e público-alvo

O Eco-Report busca facilitar o registro de ocorrências ambientais, permitindo que cidadãos registrem situações que merecem atenção, como descarte irregular de resíduos e poluição.

O público-alvo são cidadãos que desejam registrar ocorrências e responsáveis pela análise dessas informações.

## Regras de negócio

1. Uma ocorrência deve estar associada a uma categoria existente.
2. Uma ocorrência deve estar associada a um usuário existente.
3. O status de uma ocorrência deve ser `PENDING`, `IN_ANALYSIS` ou `RESOLVED`.
4. O sistema impede o cadastro de usuários com e-mail já registrado.
5. A exclusão de uma ocorrência é lógica: o registro é marcado como excluído, sem ser removido fisicamente do banco de dados.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL 17
- Flyway
- Maven
- Docker

## Estrutura do projeto

O código-fonte é organizado por entidades e responsabilidades:

- `user/`: gerenciamento de usuários.
- `category/`: consulta de categorias de ocorrências.
- `report/`: registro e consulta de ocorrências ambientais.
- `health/`: endpoint de verificação da aplicação.
- `GlobalExceptionHandler.java`: tratamento centralizado de erros da API.
- `src/main/resources/db/migration/`: scripts de criação e evolução do banco de dados.
- `Dockerfile`: build da imagem da aplicação.
- `compose.yaml`: serviços do PostgreSQL e da aplicação.

Dentro dos módulos, as classes são separadas conforme suas responsabilidades, incluindo entidades, repositórios, DTOs, mapeadores, serviços e controladores.

## Rotas da API

| Método | Rota                       | Descrição                                   |
| ------ | -------------------------- | ------------------------------------------- |
| GET    | `/api/health`              | Verifica se a aplicação está no ar          |
| GET    | `/api/categories`          | Lista categorias                            |
| POST   | `/api/users`               | Cadastra um usuário                         |
| GET    | `/api/users`               | Lista usuários                              |
| GET    | `/api/users/{id}`          | Consulta um usuário                         |
| POST   | `/api/reports`             | Registra uma ocorrência                     |
| GET    | `/api/reports`             | Lista ocorrências                           |
| GET    | `/api/reports/{id}`        | Consulta uma ocorrência                     |
| PATCH  | `/api/reports/{id}/status` | Atualiza o status de uma ocorrência         |
| DELETE | `/api/reports/{id}`        | Realiza a exclusão lógica de uma ocorrência |

As rotas de consulta de ocorrências não devem retornar registros excluídos logicamente.

## Como executar

### Pré-requisitos

- JDK 21
- Docker e Docker Compose

### Opção 1: tudo com Docker

Na raiz do projeto, execute:

```bash
docker compose up -d
```

O `compose.yaml` sobe dois serviços: o PostgreSQL (`postgres`) e a aplicação (`app`), construída a partir do `Dockerfile`.

### Opção 2: banco no Docker e aplicação local

Suba apenas o banco de dados:

```bash
docker compose up -d postgres
```

Em seguida, no Windows, execute a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

O PostgreSQL fica exposto na porta `5440` do host, e a aplicação utiliza a porta `8081`.

As tabelas do banco de dados são gerenciadas pelo Flyway durante a inicialização.

## Validação

Para compilar o projeto e executar os testes configurados:

```powershell
.\mvnw.cmd clean package
```

## Documentação complementar

- `docs/modelo-de-dominio.md`: entidades, relacionamentos e diagrama do modelo de domínio.
- `docs/contrato-da-api.md`: rotas, formatos de entrada e saída e exemplos de requisições.
- `docs/autoavaliacao.md`: avaliação dos requisitos implementados e pendentes.
