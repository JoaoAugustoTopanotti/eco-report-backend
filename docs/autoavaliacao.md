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
- [x] Armazenamento dos metadados das imagens no PostgreSQL.
- [x] Armazenamento dos arquivos de imagem em volume Docker.

## 3. API e regras de negócio

- [x] Cadastro e consulta de usuários.
- [x] Consulta de categorias.
- [x] Cadastro e consulta de ocorrências ambientais.
- [x] Validação de campos obrigatórios.
- [x] Validação da existência do usuário e da categoria associados a uma ocorrência.
- [x] Validação dos status permitidos para as ocorrências.
- [x] Impedimento de cadastro de usuários com e-mail duplicado.
- [x] Tratamento centralizado de erros com respostas HTTP apropriadas.
- [x] Exclusão lógica de ocorrências.
- [x] Atualização do status de ocorrências.
- [x] Envio, listagem e recuperação de imagens das ocorrências.
- [x] Validação de formato e tamanho dos arquivos enviados.
- [x] Impedimento de envio de imagens para ocorrências excluídas logicamente.

## 4. Execução e validação

- [x] Aplicação compilada com sucesso por meio do Maven Wrapper.
- [x] Banco de dados executado em contêiner Docker.
- [x] Configuração Docker da aplicação e do banco de dados.
- [x] Testes manuais do fluxo principal de imagens.
- [x] Testes manuais de validações de upload, incluindo arquivo vazio, formato inválido, tamanho acima do limite, ocorrência inexistente e ocorrência excluída.
- [ ] Revisão final dos requisitos, das rotas e das regras de negócio.
- [ ] Conferência final da documentação em relação à implementação atual.

## 5. Funcionalidades pendentes ou fora do escopo atual

- [ ] Implementação de autenticação e autorização de usuários.
- [ ] Interface frontend para interação com a API.
- [ ] Revisão e ampliação dos testes automatizados.

A autenticação, a autorização e a interface frontend não fazem parte do escopo implementado nesta etapa. O desenvolvimento prioriza os requisitos essenciais da disciplina e as regras de negócio definidas para o projeto.

## 6. Considerações finais

O Eco-Report implementa uma API REST para registrar e consultar ocorrências ambientais, utilizando Java, Spring Boot e PostgreSQL. O projeto aplica separação de responsabilidades, DTOs, mapeadores, persistência com JPA e migrações com Flyway.

O módulo de imagens permite anexar e consultar evidências visuais das ocorrências, com validações de formato e tamanho e armazenamento persistente dos arquivos em volume Docker.

A etapa final consiste em revisar os requisitos da atividade, conferir as rotas e regras de negócio e garantir que a documentação corresponda ao comportamento real da API.
