INSERT INTO pais (id_pais, created_at, nombre_pais, updated_at) VALUES (1,  '2025-05-06 00:39:00.000', 'Chile', NULL    )
ON CONFLICT (id_pais) DO NOTHING;