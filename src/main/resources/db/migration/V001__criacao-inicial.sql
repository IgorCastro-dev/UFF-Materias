CREATE TABLE if not exists `usuario_tipo`(
                                          `usuario_tipo_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `nome` CHAR(255) NOT NULL,
                                          `descricao` CHAR(255) NOT NULL
);

CREATE TABLE if not exists `usuarios`(
                                      `usuarios_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      `nome` CHAR(255) NOT NULL,
                                      `email` CHAR(255) NOT NULL UNIQUE,
                                      `senha` CHAR(255) NOT NULL,
                                      `celular` CHAR(255) NOT NULL UNIQUE,
                                      `usuario_tipo_id` INT,
                                      FOREIGN KEY (usuario_tipo_id) REFERENCES usuario_tipo(usuario_tipo_id)
);

CREATE TABLE if not exists `materias` (
    `materias_id` INT PRIMARY KEY,
    `nome_materia` VARCHAR(255)
);

CREATE TABLE `secao_materias` (
    `secao_materias_id` INT PRIMARY KEY,
    `materias_id` INT,
    `secao_materias_nome` VARCHAR(255),
    FOREIGN KEY (materias_id) REFERENCES materias(materias_id)
);

CREATE TABLE `conteudo_secao` (
    `conteudo_secao_id` INT PRIMARY KEY,
    `secao_materias_id` INT,
    `conteudo_da_secao` TEXT,
    FOREIGN KEY (secao_materias_id) REFERENCES secao_materias(secao_materias_id)
);


