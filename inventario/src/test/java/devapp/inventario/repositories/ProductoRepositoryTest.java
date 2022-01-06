package devapp.inventario.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import devapp.inventario.entities.Producto;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    ProductoRepository productoRepo;

    @Test
    public void testFindAllByEstadoAndCategoria()
    {
        Pageable p = PageRequest.of(0, 2);
        List<Producto> productos = productoRepo.findAllByEstadoAndCategoria_nombre(1, "utensillos", p);
        assertNotNull(productos);
        assertTrue(productos.size()==2);
       
    }

    @Test 
    public void testFindAllByEstado()
    {
        Pageable p = PageRequest.of(0, 10);
        List<Producto> productos =  productoRepo.findAllByEstado(1, p);
        assertNotNull(productos);
        assertTrue(productos.size()==10);

    }

  


}
