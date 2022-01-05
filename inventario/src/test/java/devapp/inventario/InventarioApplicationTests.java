package devapp.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



@DataJpaTest
class InventarioApplicationTests {
	@Autowired
	
	@Test
	public void testFindByLastName() {
		// Cliente cliente = new Cliente("Jack", "Bauer","","","");
		// entityManager.persist(cliente);

		// Set<Cliente> findBynombre = cl.findBynombre(cliente.getNombre());

		// assertThat(findBynombre).extracting(Cliente::getNombre).containsOnly(cliente.getNombre());
	}
}
