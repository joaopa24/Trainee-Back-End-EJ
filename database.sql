CREATE TABLE "Livro" (
  "idLivro" integer PRIMARY KEY,
  "nome" varchar(45),
  "autor" varchar(45),
  "data_lancamento" timestamp
);

CREATE TABLE "Emprestimo" (
  "idLivro" integer,
  "idPessoa" integer
);

CREATE TABLE "Pessoa" (
  "idPessoa" integer PRIMARY KEY,
  "nome" varchar(80),
  "cep" char(9)
);

ALTER TABLE "Emprestimo" ADD FOREIGN KEY ("idLivro") REFERENCES "Livro" ("idLivro");
ALTER TABLE "Emprestimo" ADD FOREIGN KEY ("idPessoa") REFERENCES "Pessoa" ("idPessoa");
