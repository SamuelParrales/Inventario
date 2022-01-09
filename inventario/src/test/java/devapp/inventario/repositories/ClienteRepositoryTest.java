package devapp.inventario.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import devapp.inventario.entities.Cliente;

@DataJpaTest
public class ClienteRepositoryTest {
    @Autowired
    ClienteRepository clienteRepo;

    @Test
    public void testFilterByNombresOrApellidos()
    {
        Pageable p = PageRequest.of(0, 2);
        List<Cliente> clientes = clienteRepo.filterByNombresOrApellidos("e", p);
        assertNotNull(clientes);
        assertTrue(clientes.size()>0);
    }

    @Test
    public void testFindAllByEstado()
    {
        Pageable p = PageRequest.of(0, 2);
        List<Cliente> clientes = clienteRepo.findAllByEstado(1, p);
        assertNotNull(clientes);
        assertTrue(clientes.size()==2);
    }



}
