package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Producto;
import devapp.inventario.entities.Proveedor;

@Repository
public interface ProductoRepository  extends PagingAndSortingRepository<Producto,Integer>
{

    //Consultas jpql
    @Query(value = "SELECT * FROM PRODUCTO p WHERE UPPER(p.NOMBRE) LIKE UPPER(concat('%',:nombre,'%')) and estado=1",
    nativeQuery = true)
    public List<Producto> filterByNombre(@Param(value = "nombre") String nombre);

        //Para la paginacion
    @Query(value = "SELECT * FROM PRODUCTO p WHERE UPPER(p.NOMBRE) LIKE UPPER(concat('%',:nombre,'%')) and estado=1",
    nativeQuery = true)
    public List<Producto> filterByNombre( String nombre,Pageable p);

    //Consultas usando jpa
    public List<Producto> findAllByEstado(int estado);
    public List<Producto> findAllByEstadoAndCategoria_nombre(int estado,String categoria);

        //Para la paginacion
    public List<Producto> findAllByEstado(int estado,Pageable p); 
    public List<Producto> findAllByEstadoAndCategoria_nombre(int estado,String categoria,Pageable p);
    public List<Producto> findAllByProveedores(Proveedor proveedor,Pageable p);//Falta probar


}
