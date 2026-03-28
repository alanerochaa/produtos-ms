CREATE TABLE produto (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100),
    preco DECIMAL(10,2)
);

CREATE TABLE usuario (
    login VARCHAR(100) PRIMARY KEY,
    nome VARCHAR(255),
    avatar_url VARCHAR(500),
    role VARCHAR(50)
);