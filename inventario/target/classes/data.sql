insert into Empleado(NOMBRES,APELLIDOS,CI,CORREO,ESTADO,PASSWORD,TIPO)
Values
('SISTEMA', '', '','',1,'','SISTEMA'),
('admindb','admindb','','admin@admin.com',1,'$2a$10$4we/faCq5nDlUiU2C7Dtp..y9Fgq/mlweQBmtu9EGoB70BB06YQwK','admin');


insert into CATEGORIA (NOMBRE,DESCRIPCION)
values ('sin especificar', 'sin especificar'),
('utensillos','todo de utensillos'),
('objetos eventos', 'necesario en eventos');

insert into producto(CANT_DISPONIBLE,CANT_PRESTADA ,DESCRIPCION ,ESTADO ,NOMBRE ,VALOR_PRESTACION ,VALOR_UNITARIO,ID_CATEGORIA,IMG)
values 
(3,0,'esto es un vaso',1,'vaso',0.5,0.75,1,'b274bbad81804211bf9baed3d2d63e9b');

insert into producto(CANT_DISPONIBLE,CANT_PRESTADA ,DESCRIPCION ,ESTADO ,NOMBRE ,VALOR_PRESTACION ,VALOR_UNITARIO,ID_CATEGORIA)
values 
(3,0,'cuchara de plastico',1,'cuchara',0.5,0.60,2),
(5,0,'mesa de 2x2',1,'mesa',5,10,1),
(3,0,'cubierto acero inoxidable',1,'cubierto',0.5,0.75,1),
(7,0,'carpa de 2x2',1,'carpa',18,25,2),
(15,0,'bandeja de alumnio',1,'bandeja',2,3,1),
(3,0,'manteles de color amarillo',1,'mantel amarillo',5,10,1),
(3,0,'vajilla de cinco platos',1,'vajilla x 5 platos',17,23,2),
(10,0,'carpa de 3x3',1,'carpa 3x3',21,30,1),
(20,0,'carpa de 4x4',1,'carpa 4x4',31,40,2),
(20,0,'carpa de 5x5',1,'carpa 4x4',31,40,2),
(20,0,'jarra',1,'jarra de vidrio',3,5,2),
(20,0,'repostero',1,'repostero grande',6,10,2);


insert into cliente(APELLIDOS,CI,CORREO,ESTADO,NOMBRES,PASSWORD,CELULAR)
VALUES
('CONSUMIDOR','','',1,'FINAL','',''),
('Parrales','1315478162','parrales.samuel@hotmail.com',1,'Samuel','$2a$10$4we/faCq5nDlUiU2C7Dtp..y9Fgq/mlweQBmtu9EGoB70BB06YQwK','0983670287');


insert into proveedor(correo,direccion,estado,nombre,telefono) 
values('','',1,'SIN ESPECIFICAR','');

insert into PRODUCTO_PROV (id_producto,id_proveedor)
values
(1,1),
(2,1),
(3,1),
(4,1);

