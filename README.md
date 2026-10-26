# Eco-Report

API REST para registro e consulta de ocorrências ambientais, desenvolvida como projeto acadêmico da disciplina de Backend da SATC.

## Problema e público-alvo

O Eco-Report busca facilitar o registro de ocorrências ambientais, como descarte irregular de resíduos e poluição, permitindo que cidadãos registrem situações que merecem atenção.

O público-alvo são cidadãos que desejam registrar ocorrências e responsáveis pela análise dessas informações.

## Regras de negócio

1. Uma ocorrência deve estar associada a uma categoria existente.
2. Uma ocorrência deve estar associada a um usuário existente.
3. O status de uma ocorrência deve ser `PENDING`, `IN_ANALYSIS` ou `RESOLVED`.
4. O sistema impede o cadastro de usuários com e-mail já registrado.
5. A exclusão de uma ocorrência é lógica: o registro é marcado como excluído, sem ser removido fisicamente do banco de dados.
6. Não é permitido adicionar imagens a uma ocorrência excluída.
7. As imagens devem estar nos formatos JPEG, PNG ou WebP e ter no máximo 5 MB.

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
- `category/`: gerenciamento e consulta de categorias.
- `report/`: registro e consulta de ocorrências ambientais.
- `reportImage/`: envio, consulta e recuperação de imagens das ocorrências.
- `collectionpoint/`: cadastro e consulta de ecopontos, caso o módulo esteja presente na versão atual.
- `health/`: endpoint de verificação da aplicação.
- `GlobalExceptionHandler.java`: tratamento centralizado de erros da API.
- `src/main/resources/db/migration/`: scripts de criação e evolução do banco de dados.
- `Dockerfile`: configuração para construir a imagem da aplicação.
- `compose.yaml`: configuração dos serviços da aplicação e do PostgreSQL.

Dentro dos módulos, as classes são separadas conforme suas responsabilidades, incluindo entidades, repositórios, DTOs, mapeadores, serviços e controladores.

## Rotas da API

| Método | Rota                             | Descrição                                   |
| ------ | -------------------------------- | ------------------------------------------- |
| GET    | `/api/health`                    | Verifica se a aplicação está no ar          |
| GET    | `/api/categories`                | Lista categorias                            |
| POST   | `/api/users`                     | Cadastra um usuário                         |
| GET    | `/api/users`                     | Lista usuários                              |
| GET    | `/api/users/{id}`                | Consulta um usuário                         |
| POST   | `/api/reports`                   | Registra uma ocorrência                     |
| GET    | `/api/reports`                   | Lista ocorrências                           |
| GET    | `/api/reports/{id}`              | Consulta uma ocorrência                     |
| PATCH  | `/api/reports/{id}/status`       | Atualiza o status de uma ocorrência         |
| DELETE | `/api/reports/{id}`              | Realiza a exclusão lógica de uma ocorrência |
| POST   | `/api/reports/{reportId}/images` | Envia uma imagem para uma ocorrência        |
| GET    | `/api/reports/{reportId}/images` | Lista as imagens de uma ocorrência          |
| GET    | `/api/report-images/{imageId}`   | Recupera o arquivo de uma imagem            |
| POST   | `/api/collection-points`         | Cadastra um ecoponto, caso implementado     |
| GET    | `/api/collection-points`         | Lista ecopontos, caso implementado          |
| GET    | `/api/collection-points/{id}`    | Consulta um ecoponto, caso implementado     |
| PUT    | `/api/collection-points/{id}`    | Atualiza um ecoponto, caso implementado     |
| DELETE | `/api/collection-points/{id}`    | Remove um ecoponto, caso implementado       |

As rotas de consulta de ocorrências não devem retornar registros excluídos logicamente.

## Como executar

### Pré-requisitos

- JDK 21, caso a aplicação seja executada localmente.
- Docker e Docker Compose.

### Opção 1: executar tudo com Docker

Na raiz do projeto, execute:

```bash
docker compose up -d --build
```

O `compose.yaml` inicia o PostgreSQL e a aplicação, construída a partir do `Dockerfile`.

### Opção 2: banco no Docker e aplicação local

Suba apenas o banco de dados:

```bash
docker compose up -d postgres
```

Em seguida, no Windows, execute a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou, caso queira popular o banco, execute a aplicação junto das seeds:

```powershell
.\mvnw spring-boot:run -Dspring-boot.run.arguments=--app.seed=true
```

O PostgreSQL fica exposto na porta `5440` do host, e a aplicação utiliza a porta `8081`.

As tabelas do banco de dados são gerenciadas pelo Flyway durante a inicialização.

### Armazenamento de imagens

Os arquivos de imagem são armazenados no volume Docker `eco_report_uploads` quando a aplicação é executada em contêiner. Os metadados das imagens ficam no PostgreSQL.

Esse volume permite preservar os arquivos mesmo quando o contêiner da aplicação é recriado.

## Validação

Para compilar o projeto e executar os testes automatizados configurados:

```powershell
.\mvnw.cmd clean package
```

As rotas também podem ser verificadas manualmente com uma ferramenta como o Postman.

## Documentação complementar

- `docs/modelo-de-dominio.md`: entidades, relacionamentos e diagrama do modelo de domínio.
- `docs/contrato-da-api.md`: rotas, formatos de entrada e saída e exemplos de requisições.
- `docs/autoavaliacao.md`: avaliação dos requisitos implementados e pendentes.
