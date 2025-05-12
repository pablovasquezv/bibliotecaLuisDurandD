-- public.genero definition

-- Drop table

-- DROP TABLE public.genero;

CREATE TABLE public.genero
(
    id_genero          bigserial NOT NULL,
    created_at         timestamp NULL,
    descripcion_genero varchar(150) NULL,
    nombre_genero      varchar(50) NULL,
    updated_at         timestamp NULL,
    CONSTRAINT genero_pkey PRIMARY KEY (id_genero),
    CONSTRAINT uk_dqrh2g2sdm6t6h6udim4yitwt UNIQUE (nombre_genero)
);