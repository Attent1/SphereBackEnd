-- Criação da tabela
CREATE TABLE IF NOT EXISTS posts (
   id BIGINT AUTO_INCREMENT,
   text VARCHAR(255),
   created_at datetime,
       PRIMARY KEY(id)
);

-- Inserção de dados
INSERT INTO posts (text, created_at) VALUES ('Primeiro post!', '2024-08-25 10:00:00');
INSERT INTO posts (text, created_at) VALUES ('Segundo post!', '2024-08-25 11:15:00');
INSERT INTO posts (text, created_at) VALUES ('Terceiro post!', '2024-08-25 12:30:00');
INSERT INTO posts (text, created_at) VALUES ('Quarto post!', '2024-08-25 13:45:00');
INSERT INTO posts (text, created_at) VALUES ('Quinto post!', '2024-08-25 15:00:00');
