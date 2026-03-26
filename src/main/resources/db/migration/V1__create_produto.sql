CREATE TABLE produto (
    id UUID NOT NULL,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    preco DECIMAL(10,2),
    categoria VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE usuario (
    login VARCHAR(255) NOT NULL,
    nome VARCHAR(255),
    avatar_url VARCHAR(500),
    role VARCHAR(100),
    PRIMARY KEY (login)
);