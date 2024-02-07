INSERT INTO usuario_tipo (usuario_tipo_id, nome, descricao)
SELECT 1, 'USER', 'Tipo de usuário padrão'
FROM usuario_tipo
WHERE usuario_tipo_id = 1
HAVING COUNT(*) = 0;

INSERT INTO usuario_tipo (usuario_tipo_id, nome, descricao)
SELECT 2, 'ADMIN', 'Tipo de usuário com privilégios administrativos'
FROM usuario_tipo
WHERE usuario_tipo_id = 2
HAVING COUNT(*) = 0;