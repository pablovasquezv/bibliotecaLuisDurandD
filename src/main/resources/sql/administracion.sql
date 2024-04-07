-- New script in postgres.
-- Date: 7 abr 2024
-- Time: 2:20:17
-- public.libro definition

-- Para cambiar el nombre de un columna de una tabla
ALTER TABLE libro RENAME column jajajar_id  TO paginas_libro;

-- Eliminar una columna
ALTER TABLE libro DROP COLUMN autor_id_;


-- Eliminar una foreign keys public.libro
ALTER TABLE public.libro ADD CONSTRAINT fkq9p1yvon11g00ojljsf1x1v4s FOREIGN KEY (autor_id_) REFERENCES public.autor(id_autor);
ALTER TABLE libro DROP CONSTRAINT fkq9p1yvon11g00ojljsf1x1v4s;

-- Obtener las columnas que forman parte de una tabla PostgreSQL
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_name = 'libro';