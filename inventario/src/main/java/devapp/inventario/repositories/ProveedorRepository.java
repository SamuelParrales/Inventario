package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Proveedor;

@Repository
public interface ProveedorRepository extends PagingAndSortingRepository<Proveedor,Integer> {
    
    //Consultas jpql
    @Query(value = 
    "SELECT * FROM PROVEEDOR p "+
    "where UPPER(p.NOMBRE) like UPPER(concat('%',:nombre,'%')) and estado=1",nativeQuery = true)
    public List<Proveedor> filterByNombre(@Param(value = "nombre") String nombre,Pageable p);    

    //jpa
    public List<Proveedor> findAllByEstado(int estado); //metodo para el borrado logico
    public List<Proveedor> findAllByNombreAndEstado(String nombres,int estado); //metodo para el borrado logico
    public Proveedor findByIdAndEstado(int id,int estado);

        //Para la paginacion
    public List<Proveedor> findAllByEstado(int estado,Pageable p); //metodo para el borrado logico
    public List<Proveedor> findAllByNombreAndEstado(String nombres,int estado,Pageable p); //metodo para el borrado logico
    
}
