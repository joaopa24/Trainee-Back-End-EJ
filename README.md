# API de Empréstimo de Livros
## Processo Trainee - Projeto Api-Rest em Java Spring Boot 
Este projeto é uma API desenvolvida em Spring Boot para gerenciar um sistema de empréstimo de livros. A API permite a criação, leitura, atualização e exclusão de livros, além de gerenciar os empréstimos associados a cada livro.

## Funcionalidades
- **Cadastro de Livros**: Permite registrar um novo livro na biblioteca.
- **Listagem de Livros**: Retorna todos os livros cadastrados na biblioteca.
- **Busca de Livros por ID**: Busca informações detalhadas de um livro utilizando seu ID.
- **Atualização de Livros**: Atualiza as informações de um livro existente.
- **Exclusão de Livros**: Permite a exclusão de um livro da biblioteca.
- **Empréstimos**: Relaciona livros aos empréstimos realizados.

## Pré-requisitos
Antes de executar o projeto, você precisa ter instalado:
- **Java 17** ou superior
- **Maven**
- **IDE** de sua preferência (IntelliJ IDEA, Eclipse, etc.)

## Como Rodar o Projeto
### Clonando o repositório
Clone este repositório para sua máquina local:

```bash
git clone https://github.com/seu-usuario/projeto-livros.git
cd projeto-livros
mvn clean install
```

### Executando
```bash
mvn spring-boot:run
```

## Documentação
A API está documentada com o Swagger. Após iniciar a aplicação, acesse a interface do Swagger para visualizar e testar os endpoints da API em:
```bash
http://localhost:8080/swagger-ui.html
```