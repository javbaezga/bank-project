INSERT INTO `persona` (`id`, `direccion`, `edad`, `nombres`, `genero`, `identificacion`, `telefono`) VALUES
	(1, 'Otavalo SN y principal', 21, 'José Lema', 'M', '1234567890', '098254785');
INSERT INTO `cliente` (`contrasena`, `estado`, `usuario`, `id`) VALUES
	('12341234', 1, 'jose.lema', 1);
INSERT INTO `cuenta` (`id`, `saldo`, `saldo_diario`, `fecha_reseteo_saldo_diario`, `saldo_inicial`, `numero`, `estado`, `tipo`, `cliente_id`) VALUES
	(1, 500.00, 1000.00, '2023-05-07', 500.00, '000001', 1, 'A', 1);