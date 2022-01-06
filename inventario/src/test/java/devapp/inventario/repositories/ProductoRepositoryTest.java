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
import devapp.inventario.entities.Proveedor;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    ProductoRepository productoRepo;

    @Autowired
    ProveedorRepository proveedorRepo;

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

  
    @Test
    public void testFilterByName()
    {
        Pageable p= PageRequest.of(0, 2);
        List<Producto> productos = productoRepo.filterByNombre("v",p);
    
        assertNotNull(productos);
        assertTrue(productos.size()==2);
    }

    @Test
    public void testFindAllByProveedores()
    {
        Pageable p = PageRequest.of(0, 2);
        Proveedor proveedor = proveedorRepo.findById(1).get(); //id=1: Sin especificar
        List<Producto> productos = productoRepo.findAllByProveedores(proveedor,p);
        assertNotNull(productos);
        assertTrue(productos.size()==2);
    }

}
