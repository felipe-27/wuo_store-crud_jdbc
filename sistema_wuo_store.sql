SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS wuostore CHARACTER SET utf8mb4;
USE wuostore;

CREATE TABLE produto (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nomeProduto VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL DEFAULT 0,
    avaliacao VARCHAR(3) NOT NULL CHECK (avaliacao IN ('Mau', 'Bom')),
    protegido BOOLEAN NOT NULL DEFAULT 0
);

-- Impede apagar os produtos padrão
DELIMITER //
CREATE TRIGGER trg_produto_protegido BEFORE DELETE ON produto
FOR EACH ROW
BEGIN
    IF OLD.protegido = 1 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Produto padrão não pode ser removido.';
    END IF;
END//
DELIMITER ;

INSERT INTO produto (nomeProduto, preco, quantidade, avaliacao, protegido)
VALUES
('Wuo Queijo Futurista™', 49.90, 100, 'Bom', 1),
('WuoDrive™', 199.90, 100, 'Bom', 1);
