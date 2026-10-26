# Contrato da API — Eco-Report

## 1. Visão geral

A API do Eco-Report permite cadastrar usuários, consultar categorias e registrar e consultar ocorrências ambientais.

- **Base URL local:** `http://localhost:8081`
- **Formato dos dados:** JSON
- **Content-Type:** `application/json` nas requisições com corpo JSON.

## 2. Rotas disponíveis

| Método | Rota                       | Descrição                                   |
| ------ | -------------------------- | ------------------------------------------- |
| GET    | `/api/health`              | Verifica o estado da aplicação              |
| GET    | `/api/categories`          | Lista categorias                            |
| POST   | `/api/users`               | Cadastra um usuário                         |
| GET    | `/api/users`               | Lista usuários                              |
| GET    | `/api/users/{id}`          | Consulta um usuário pelo ID                 |
| POST   | `/api/reports`             | Registra uma ocorrência                     |
| GET    | `/api/reports`             | Lista ocorrências                           |
| GET    | `/api/reports/{id}`        | Consulta uma ocorrência pelo ID             |
| PATCH  | `/api/reports/{id}/status` | Atualiza o status de uma ocorrência         |
| DELETE | `/api/reports/{id}`        | Realiza a exclusão lógica de uma ocorrência |

### GET `/api/health`

Verifica se a aplicação está no ar.

**Exemplo de resposta (`200 OK`):**

```json
{
  "status": "UP"
}
```

## 3. Usuários

### POST `/api/users`

Cadastra um usuário. Em caso de sucesso, retorna `201 Created` com os dados do usuário criado.

**Exemplo de requisição:**

```json
{
  "name": "Maria Silva",
  "email": "maria@example.com",
  "password": "senha123"
}
```

**Exemplo de resposta:**

```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria@example.com",
  "role": "CITIZEN",
  "createdAt": "2026-10-25T14:30:00"
}
```

A senha é armazenada em formato de hash e não deve ser retornada pela API.

**Possíveis erros:**

- `400 Bad Request`: dados inválidos.
- `409 Conflict`: e-mail já cadastrado.

### GET `/api/users`

Retorna a lista de usuários cadastrados.

**Exemplo de resposta:**

```json
[
  {
    "id": 1,
    "name": "Maria Silva",
    "email": "maria@example.com",
    "role": "CITIZEN",
    "createdAt": "2026-10-25T14:30:00"
  }
]
```

### GET `/api/users/{id}`

Retorna os dados do usuário correspondente ao ID informado.

**Possíveis erros:**

- `404 Not Found`: usuário não encontrado.

## 4. Categorias

### GET `/api/categories`

Retorna a lista de categorias disponíveis no sistema.

**Exemplo de resposta:**

```json
[
  {
    "id": 1,
    "name": "Descarte irregular de resíduos",
    "status": "ACTIVE"
  },
  {
    "id": 2,
    "name": "Poluição da água",
    "status": "ACTIVE"
  }
]
```

As categorias são utilizadas para classificar as ocorrências ambientais. O cadastro de categorias por meio da API não faz parte das rotas atuais.

## 5. Ocorrências ambientais

### POST `/api/reports`

Registra uma nova ocorrência ambiental. Em caso de sucesso, retorna `201 Created` com os dados da ocorrência criada.

**Exemplo de requisição:**

```json
{
  "title": "Descarte irregular de lixo",
  "description": "Há resíduos descartados irregularmente próximo ao rio.",
  "location": "Rua das Flores, próximo à ponte",
  "categoryId": 1,
  "userId": 1
}
```

O status inicial é `PENDING`, definido pela lógica de criação da ocorrência.

**Exemplo de resposta:**

```json
{
  "id": 1,
  "title": "Descarte irregular de lixo",
  "description": "Há resíduos descartados irregularmente próximo ao rio.",
  "location": "Rua das Flores, próximo à ponte",
  "categoryName": "Descarte irregular de resíduos",
  "status": "PENDING",
  "createdAt": "2026-10-25T14:30:00",
  "updatedAt": "2026-10-25T14:30:00"
}
```

**Possíveis erros:**

- `400 Bad Request`: campos inválidos ou referência a usuário ou categoria inexistente.
- `404 Not Found`: ocorrência não encontrada em operações que exigem um ID existente.

### GET `/api/reports`

Retorna a lista de ocorrências não excluídas logicamente.

**Exemplo de resposta:**

```json
[
  {
    "id": 1,
    "title": "Descarte irregular de lixo",
    "description": "Há resíduos descartados irregularmente próximo ao rio.",
    "location": "Rua das Flores, próximo à ponte",
    "categoryName": "Descarte irregular de resíduos",
    "status": "PENDING",
    "createdAt": "2026-10-25T14:30:00",
    "updatedAt": "2026-10-25T14:30:00"
  }
]
```

### GET `/api/reports/{id}`

Retorna os dados de uma ocorrência específica, desde que ela não tenha sido excluída logicamente.

**Possível erro:**

- `404 Not Found`: ocorrência inexistente ou excluída logicamente.

### PATCH `/api/reports/{id}/status`

Atualiza o status de uma ocorrência.

**Exemplo de requisição:**

```json
{
  "status": "IN_ANALYSIS"
}
```

Os valores permitidos são:

- `PENDING`: ocorrência pendente.
- `IN_ANALYSIS`: ocorrência em análise.
- `RESOLVED`: ocorrência resolvida.

**Possíveis erros:**

- `400 Bad Request`: status inválido.
- `404 Not Found`: ocorrência inexistente ou excluída logicamente.

### DELETE `/api/reports/{id}`

Realiza a exclusão lógica da ocorrência. O registro permanece no banco de dados, mas deixa de aparecer nas consultas normais.

Em caso de sucesso, retorna `204 No Content`, sem corpo na resposta.

**Possível erro:**

- `404 Not Found`: ocorrência inexistente ou já indisponível para exclusão.

## 6. Tratamento de erros

Os erros da API são tratados centralmente e retornam uma mensagem explicativa em JSON.

**Exemplo de resposta de erro:**

```json
{
  "message": "Mensagem explicando o erro."
}
```

Os principais códigos HTTP utilizados são:

| Código            | Significado                                 |
| ----------------- | ------------------------------------------- |
| `200 OK`          | Requisição processada com sucesso           |
| `201 Created`     | Recurso criado com sucesso                  |
| `204 No Content`  | Sucesso, sem corpo na resposta              |
| `400 Bad Request` | Dados inválidos ou regra de negócio violada |
| `404 Not Found`   | Recurso não encontrado                      |
| `409 Conflict`    | Conflito, como e-mail já cadastrado         |

Os códigos efetivamente retornados dependem da implementação de cada controlador e serviço.
