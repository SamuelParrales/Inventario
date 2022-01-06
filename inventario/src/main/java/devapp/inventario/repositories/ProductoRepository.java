package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Producto;
import devapp.inventario.entities.Proveedor;

@Repository
public interface ProductoRepository  extends PagingAndSortingRepository<Producto,Integer>
{
    public List<Producto> findAllByEstado(int estado,Pageable p); //metodo para el borrado logico
    public List<Producto> findAllByEstadoAndCategoria_nombre(int estado,String categoria,Pageable p);
    // public List<Producto> findAllByProveedores(Proveedor proveedor);


    //Metodos para las paginas
    public List<Producto> findAllByEstado(int estado);
    public List<Producto> findAllByEstadoAndCategoria_nombre(int estado,String categoria);
    
    //Probar estrictamente
    public List<Producto> findAllByProveedores(Proveedor proveedor,Pageable p);
}
