package devapp.inventario;

import java.util.ArrayList;
import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import devapp.inventario.entities.Categoria;
import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.DetRecepPrest;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.EstRecepPrest;
import devapp.inventario.entities.Producto;
import devapp.inventario.entities.Proveedor;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.CategoriaRepository;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.DetRecepPrestRepository;
import devapp.inventario.repositories.EmpleadoRepository;
import devapp.inventario.repositories.ProductoRepository;
import devapp.inventario.repositories.ProveedorRepository;
import devapp.inventario.repositories.RecepPrestRepository;

@Transactional
@SpringBootApplication
public class InventarioApplication implements CommandLineRunner{

	@Autowired
	CategoriaRepository categoriaRepo;
	@Autowired 
	ProductoRepository productoRepo;
	@Autowired 
	ProveedorRepository proveedorRepo;
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired 
	RecepPrestRepository recepPrestRepo;
	@Autowired
	EmpleadoRepository empleadoRepo;
	@Autowired
	DetRecepPrestRepository detallesRepo;

	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{
		//*******************Probando el guardado de la la categoria y el producto
		Categoria categoria  = new Categoria("Vidrios","Vidrierias");
		categoria = categoriaRepo.save(categoria);

		Producto producto = new Producto("Aguardiente", "Rico rico", 1.5, 15, 2, categoria);
		producto = productoRepo.save(producto);


			//Probando el borrado de producto y la categoria
		
		
			// productoRepo.deleteById((long)1);
			// categoriaRepo.deleteById(1);				
								//************Funcionó bien */
		
			//Probando edicion de la categoria y producto
		categoria.setDescripcion("Ya no hay hoy");
		producto.setCantDisponible(15);
		// categoriaRepo.save(categoria);
		// productoRepo.save(producto);



		//*******************Probando el guardado del proveedor
		Proveedor proveedor1 = new Proveedor("Elver", "Dadero", "0983670287", "Tuyasabes@s.com");
		Proveedor proveedor2 = new Proveedor("Elena", "Nito", "0987654321", "Nou@safa.com");  

		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		proveedores.add(proveedor1);
		proveedores.add(proveedor2);

		proveedores = (List<Proveedor>) proveedorRepo.saveAll(proveedores);

		//*******************Probando ManyToMany de Producto a Proveedor */
		
		Producto product = new Producto("Vaso", "Copa de vino", 1.75, 10, 2, categoria);
	
		product.setProveedores(proveedores);

		productoRepo.save(product);
			//Verificando sus proveedores 
		
		Producto productoConsulta = productoRepo.findById(2).get();
		
	
		for(Proveedor p : productoConsulta.getProveedores())
		{
			System.out.println(p.getNombre());
	  	}
		  	//Eliminando un proveedor del producto
		proveedor1 = proveedores.get(0);
		productoConsulta.getProveedores().remove(proveedor1);
		
			//Eliminando producto (sin funka)

		//   productoRepo.delete(productoConsulta);



			//Probando cliente

		Cliente cliente = new Cliente("1309027579", "elsa", "natorio","elsa@hotmail.com", "12345");

		cliente = clienteRepo.save(cliente);

			//Modificando el cliente
		cliente.setNombres("rosa");

		//Insertando Empleado
		Empleado empleado = new Empleado("09832", "despachador", "Huho","s", "sada@asfa.com", "12345");
		empleado = empleadoRepo.save(empleado);
		//Insertando prestacion
		
		List<DetRecepPrest> detalles = new ArrayList<DetRecepPrest>();
		RecepPrest recepPrest = new RecepPrest();
		EstRecepPrest estado = new EstRecepPrest(recepPrest, 4, empleado);
		
		detalles.add(new DetRecepPrest(recepPrest,product,5,20));
		detalles.add(new DetRecepPrest(recepPrest,producto,5,10));

		recepPrest.setCliente(cliente);
		recepPrest.setValorPagado(30);
		recepPrest.setTotalPrestacion(30);
		recepPrest.setDetalles(detalles);
		recepPrest.prestacion(estado);
		recepPrestRepo.save(recepPrest);

		// Consultando prestacion
		RecepPrest rP = recepPrestRepo.findById((long) 1).get();

		//List<DetRecepPrest> detallesEliminar = new ArrayList<DetRecepPrest>();
		for(DetRecepPrest d:rP.getDetalles())
		{
			System.out.println(d.getProducto().getNombre());
			
		}
		List<DetRecepPrest> d = rP.getDetalles();
		d.removeAll(d);
		d = new ArrayList<DetRecepPrest>();
		d.add(new DetRecepPrest(recepPrest,producto,5,10));


		

		

		
		
		estado = new EstRecepPrest(recepPrest, 0, empleado);
			//Realizando recepcion

		rP.cambiarEstado(estado);
		

		System.out.println(rP.estadoActual());
		//Borrando  prestacion

		
	}

}