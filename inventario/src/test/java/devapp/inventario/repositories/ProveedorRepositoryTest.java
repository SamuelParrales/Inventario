package devapp.inventario.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import devapp.inventario.entities.Proveedor;

@DataJpaTest
public class ProveedorRepositoryTest {
    @Autowired
    ProveedorRepository proveedorRepo;

    @Test
    public void testFilterByNombre()
    {
        Pageable p = PageRequest.of(0, 2);
        List<Proveedor> proveedores = proveedorRepo.filterByNombre("a",p);
        assertNotNull(proveedores);
        assertTrue(proveedores.size()>0);
    }

    @Test
    public void testFindAllByEstado()
    {
        Pageable p = PageRequest.of(0, 2);
        List<Proveedor> proveedores = proveedorRepo.findAllByEstado(1, p);

        assertNotNull(proveedores);
        assertTrue(proveedores.size()>0);
    }
}
