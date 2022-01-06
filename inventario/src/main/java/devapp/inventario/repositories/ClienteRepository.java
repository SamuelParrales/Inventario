package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Cliente;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente,Integer> 
{
    @Query(value = 
    "select * from CLIENTE c where "+
    "(UPPER(c.nombres) LIKE UPPER(concat('%',:palabra,'%')) "+
    "or UPPER(c.apellidos) LIKE UPPER(concat('%',:palabra,'%'))) "+ 
    "and estado=1",
    nativeQuery = true)
    public List<Cliente> filterByNombresOrApellidos(@Param("palabra") String palabra,Pageable p);

    public Cliente findByCi(String ci);
    public Cliente findByCorreo(String correo);
    public List<Cliente> findAllByEstado(int estado); //metodo para el borrado logico

    public List<Cliente> findAllByEstado(int estado,Pageable p);
}
