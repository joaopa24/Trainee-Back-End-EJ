CREATE TABLE Livro (
  idLivro SERIAL PRIMARY KEY, 
  nome VARCHAR(45) NOT NULL,
  autor VARCHAR(45) NOT NULL,
  data_de_lancamento DATE NOT NULL, 
  situacao BOOLEAN
);

CREATE TABLE Pessoa (
  idPessoa SERIAL PRIMARY KEY, 
  nome VARCHAR(80) NOT NULL,
  cep CHAR(9) NOT NULL,
  email VARCHAR(80) NOT NULL UNIQUE, 
  password VARCHAR(100) NOT NULL
);

CREATE TABLE Emprestimo (
  idEmprestimo SERIAL PRIMARY KEY, 
  idLivro INTEGER NOT NULL,
  idPessoa INTEGER NOT NULL, 
  FOREIGN KEY (idLivro) REFERENCES Livro(idLivro),
  FOREIGN KEY (idPessoa) REFERENCES Pessoa(idPessoa)
);
