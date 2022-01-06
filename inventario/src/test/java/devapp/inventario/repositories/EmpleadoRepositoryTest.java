package devapp.inventario.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import devapp.inventario.entities.Empleado;

@DataJpaTest
public class EmpleadoRepositoryTest {
    @Autowired
    EmpleadoRepository empleadoRepo;

    @Test
    public void testFilterByNombresOrApellidos()
    {
        Pageable p = PageRequest.of(0, 2);
        List<Empleado> empleados=  empleadoRepo.filterByNombresOrApellidos("s", p);
        assertNotNull(empleados);
        assertTrue(empleados.size()>0);
    }
}
