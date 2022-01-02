package devapp.inventario.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import devapp.inventario.entities.Cliente;
import devapp.inventario.repositories.ClienteRepository;

class ClienteServiceTest {
	//mockeando el repositorio
	@Mock
	ClienteRepository clienteRepository;
	ClienteService service;
	Cliente cliente;
	ArrayList<Cliente> clientes;
	Optional<Cliente> optional;
	
	@BeforeEach
	void setUp() throws Exception {
		//definiendo las clases de mockito
		clienteRepository = Mockito.mock(ClienteRepository.class);
		//inicializando el servicio 
		service = new ClienteService(clienteRepository);
		//incializando el cliente 
		cliente = new Cliente(1,"23xx","Eduardo","Smith","tem@email.ec","123456");
		Cliente client = new Cliente (2,"50xx","Alan","Brito","email19@em.ec","12***");
		//incializando el ArrayList
		clientes = new ArrayList<Cliente>();
		clientes.add(cliente); //agregando dos clientes al arrayList
		clientes.add(client);
		//incializando el optional 
		optional = Optional.of(cliente); 

		//definiendo el comportamiento GetClientes
		when(this.clienteRepository.findAll()).thenReturn(clientes);
		//definiendo el comportamiento saveClientes   
		when(this.clienteRepository.save(cliente)).thenReturn(cliente);
		//definiendo el comportamiento de GetById
		when(this.clienteRepository.findById(1)).thenReturn(optional);
		//definienfo el comportamiento encontrar por correo
		when(this.clienteRepository.findByCorreo("tem@email.ec")).thenReturn(optional);


	}

	@Test
	void getClientestest() {
		ArrayList<Cliente> ClientesTemp = this.service.getClientes();
		boolean comparacion = ClientesTemp.equals(this.clientes);
		Assertions.assertEquals(true, comparacion);
	}

	/*@Test
	void saveCategoriastest() {
		Cliente Temp = this.service.saveCliente(cliente); //llamando al metodo implementado en services
		Assertions.assertEquals(cliente.getId(), Temp.getId()); //comprobando los id
		Assertions.assertEquals(cliente.getNombres(), Temp.getNombres()); //comprobando el nombre
		Assertions.assertEquals(cliente.getApellidos(), Temp.getApellidos()); // comprobando el apellido
	}*/

	@Test
	void getByIdtest(){
		Cliente c = new Cliente();
		try {
			c = this.service.getById(1); //compruebo la existencia 
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean equals = c.equals(optional.get());
		Assertions.assertEquals(true,equals);
	}

	@Test
	void checkCorreotest() throws Exception{
		boolean result = this.service.checkCorreo(cliente);
		assertEquals(true, result);
	}

	@Test
	void updateClientetest() throws Exception{
		Cliente clienteTemp = this.service.updateCliente(cliente);
		boolean result = clienteTemp.equals(cliente);
		assertEquals(true, result);
	}


}
