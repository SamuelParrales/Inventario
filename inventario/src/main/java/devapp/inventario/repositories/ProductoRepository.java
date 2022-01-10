package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Categoria;
import devapp.inventario.entities.Producto;
import devapp.inventario.entities.Proveedor;

@Repository
public interface ProductoRepository  extends PagingAndSortingRepository<Producto,Integer>
{

    //Consultas jpql
    @Query(value = "SELECT COUNT(*) FROM PRODUCTO p WHERE UPPER(p.NOMBRE) LIKE UPPER(concat('%',:nombre,'%')) and estado=1",
    nativeQuery = true)
    public Long countFilterByNombre(@Param(value = "nombre") String nombre);

    @Query(value = "SELECT COUNT(*) FROM PRODUCTO p WHERE UPPER(p.NOMBRE) LIKE UPPER(concat('%',:nombre,'%')) and estado=1 and id_categoria = :id",
    nativeQuery = true)
    public Long countFilterByNombreFindCategoria_Id(@Param(value = "nombre") String nombre, @Param(value = "id") Integer id);

        //Para la paginacion
    @Query(value = "SELECT * FROM PRODUCTO p WHERE UPPER(p.NOMBRE) LIKE UPPER(concat('%',:nombre,'%')) and estado=1",
    nativeQuery = true)
    public List<Producto> filterByNombre( String nombre,Pageable p);

    @Query(value = "SELECT * FROM PRODUCTO p WHERE UPPER(p.NOMBRE) LIKE UPPER(concat('%',:nombre,'%')) and estado=1 and id_categoria=:id",
    nativeQuery = true)
    public List<Producto> filterByNombreFindCategoria_Id(String nombre,int id, Pageable p);

    
    
    
    //Consultas usando jpa
    public List<Producto> findAllByEstado(int estado);
    public List<Producto> findAllByEstadoAndCategoria_nombre(int estado,String categoria);
    public Long countByEstadoAndCategoria(int estado,Categoria categoria); 
    public Long countByEstado(int estado);
    //Para la paginacion
    public List<Producto> findAllByEstado(int estado,Pageable p); 
    public List<Producto> findAllByEstadoAndCategoria_nombre(int estado,String categoria,Pageable p);
    public List<Producto> findAllByProveedores(Proveedor proveedor,Pageable p);//Falta probar

//

}
