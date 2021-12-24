package devapp.inventario;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import devapp.inventario.entities.Cliente;
import devapp.inventario.repository.ClienteRepo;

@DataJpaTest
class InventarioApplicationTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ClienteRepo cl;
	
	@Test
	public void testFindByLastName() {
		Cliente cliente = new Cliente("Jack", "Bauer","","","");
		entityManager.persist(cliente);

		Set<Cliente> findBynombre = cl.findBynombre(cliente.getNombre());

		assertThat(findBynombre).extracting(Cliente::getNombre).containsOnly(cliente.getNombre());
	}

	

}
