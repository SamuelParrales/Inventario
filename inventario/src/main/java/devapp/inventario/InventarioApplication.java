package devapp.inventario;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import devapp.inventario.entities.*;
import devapp.inventario.repository.*;




@SpringBootApplication
public class InventarioApplication {
	private static final Logger log = LoggerFactory.getLogger(InventarioApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	
		
	}
	

	@Bean
	public CommandLineRunner demo(ClienteRepo cl, CategoriaRepo ca, EmpleadoRepo emp, RecepcionDespachoRepo repdes, 
			ProductoRepo prRepo, ProveedoresRepo pro ) {
	return (args) -> {
		//opreaciones en cliente
		Proveedores proveedor = new Proveedores("ASD", "Calle 1","9999","user1.xl");
		pro.save(proveedor); //guarda cliente
		log.info("--proveedor Almacenado--");
		
		cl.save(new Cliente("Eduard", "Mendi","lhjuh@xlm.com","user1","1234")); //guarda cliente
		log.info("--Usuario Almacenado--");
		
		ca.save(new Categoria("Vidrios","Son vidrios"));
		log.info("--Categoria almacenada--");
		
		Empleado empleado= new Empleado("1315478162","Admin","Luis","Dominguez","Qda@afss.com","admin");
		emp.save(empleado);
		log.info("--Empleado almacenado--");
		
		Categoria cat = ca.findById(1).orElseThrow( () -> new IllegalArgumentException("invalid product id: "+1));; //buscando categoria por ID
		//operaciones en producto
		Producto prod = new Producto("Vaso","Vaso de vidrio",1,1,null,cat);

		prRepo.save(prod);
		log.info("--producto almacenado--");
		
		Cliente cliente = cl.findById(1).orElseThrow( () -> new IllegalArgumentException("invalid product id: "+1)); //buscando un cliente por ID
		Recepciondespacho recepcion = new Recepciondespacho(2,2,cliente);
		repdes.save(recepcion);
		log.info("--Recepcion despacho almacenado--");

			log.info("Mostrando clientes");
			log.info("-------------------------------");
			for (Cliente c : cl.findAll()) {
				log.info(c.toString());
			}
			
			log.info("Mostrando Proveedor");
			log.info("-------------------------------");
			for (Proveedores provee : pro.findAll()) {
					log.info(provee.toString());
			}
			
			
			log.info("Mostrando categoria");
			log.info("-------------------------------");
			for (Categoria c : ca.findAll()) {
				log.info(c.toString());
			}
			
			
			log.info("Mostrando empleado");
			log.info("-------------------------------");
			for (Empleado e : emp.findAll()) {
				log.info(e.toString());
			}
			
	
			log.info("Mostrando Recepcion");
			log.info("-------------------------------");				
			for (Recepciondespacho r : repdes.findAll()) {
				log.info(r.toString());
			}
			
			log.info("Mostrando Productos");
			log.info("-------------------------------");
			for (Producto p : prRepo.findAll()) {
					log.info(p.toString());
			}
		};
	}
}