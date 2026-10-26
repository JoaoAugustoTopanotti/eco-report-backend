# Autoavaliação — Eco-Report

## 1. Estrutura do projeto

- [x] Projeto organizado por entidades e responsabilidades.
- [x] Entidades mapeadas com JPA.
- [x] Repositórios para acesso ao banco de dados.
- [x] DTOs de entrada e saída.
- [x] Mappers para conversão entre entidades e DTOs.
- [x] Services para operações e regras de negócio.
- [x] Controllers para disponibilizar as rotas HTTP.
- [x] Comentários explicativos nas classes.

## 2. Banco de dados

- [x] PostgreSQL configurado para persistência.
- [x] Migração inicial criada com Flyway.
- [x] Relacionamentos entre usuários, categorias e ocorrências definidos.
- [x] Validações de integridade definidas no banco de dados.

## 3. API e regras de negócio

- [x] Cadastro e consulta de usuários.
- [x] Consulta de categorias.
- [x] Cadastro e consulta de ocorrências ambientais.
- [x] Validação de campos obrigatórios.
- [x] Validação da existência do usuário e da categoria associados a uma ocorrência.
- [x] Validação dos status permitidos para as ocorrências.
- [x] Impedimento de cadastro de usuários com e-mail duplicado.
- [x] Tratamento de erros com respostas HTTP apropriadas.
- [x] Exclusão lógica de ocorrências.
- [x] Atualização do status de ocorrências.

## 4. Execução e validação

- [x] Aplicação compilada com sucesso por meio do Maven Wrapper.
- [x] Banco de dados executado em contêiner Docker.
- [x] Configuração Docker do projeto.
- [ ] Revisão final dos testes das rotas e das regras de negócio.

## 5. Funcionalidades pendentes ou fora do escopo atual

- [ ] Fluxo funcional de gerenciamento de imagens das ocorrências.
- [ ] Implementação de autenticação e autorização de usuários.
- [ ] Interface frontend para interação com a API.

Essas funcionalidades não fazem parte do fluxo funcional atual da API. O desenvolvimento prioriza os requisitos essenciais da disciplina e as regras de negócio definidas para o projeto.

## 6. Considerações finais

O Eco-Report implementa uma API REST para registrar e consultar ocorrências ambientais, utilizando Java, Spring Boot e PostgreSQL. O projeto aplica separação de responsabilidades, DTOs, mapeadores, persistência com JPA e migrações com Flyway.

A próxima etapa é realizar uma revisão final dos requisitos, testar as rotas e conferir a documentação para garantir que ela corresponda à implementação atual.