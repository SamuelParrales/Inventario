package devapp.inventario.repositories;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.RecepPrest;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepPrestRepository extends PagingAndSortingRepository<RecepPrest,Long> 
{
    RecepPrest findByIdAndCliente(Long id, Cliente cliente);
    List<RecepPrest> findAllByClienteOrderByIdDesc(Cliente cliente);
    List<RecepPrest> findAllByEstados_EstadoOrderByIdDesc(int estado);
    Set<RecepPrest> findAllByEstados_EmpleadoOrderByIdDesc(Empleado empleado);
       
    //Metodos para la paginacion
    List<RecepPrest> findAllByClienteOrderByIdDesc(Cliente cliente,Pageable p);
    List<RecepPrest> findAllByEstados_EstadoOrderByIdDesc(int estado,Pageable p);
    List<RecepPrest> findAllByEstados_EmpleadoAndEstados_EstadoOrderByIdDesc(Empleado empleado,int estado, Pageable p);

    @Query(value = "SELECT *  FROM RECEPCIÓN_PRESTACIÓN "+
    "where ID_RECP_PREST "+ 
    "and ID_RECP_PREST NOT in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+ 
    "WHERE ESTADO=0) "+ 
    "and  ID_RECP_PREST "+ 
    "in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+
    "WHERE ESTADO=3)",nativeQuery = true)
    List<RecepPrest> findAllPrestacion(Pageable p);

    @Query(value = "SELECT *  FROM RECEPCIÓN_PRESTACIÓN "+
    "where  ID_RECP_PREST =:id "+
    "and ID_RECP_PREST NOT in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+ 
    "WHERE ESTADO=0) "+ 
    "and  ID_RECP_PREST "+  
    "in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+
    "WHERE ESTADO=3)",nativeQuery = true)
    RecepPrest findPrestacionById(@Param(value = "id") Long id);

    @Query(value = "SELECT *  FROM RECEPCIÓN_PRESTACIÓN "+ 
    "where  id_cliente =:id "+
    "and ID_RECP_PREST NOT in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+
    "WHERE ESTADO=0) "+ 
    "and  ID_RECP_PREST "+ 
    "in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+
    "WHERE ESTADO=3) "+
    "ORDER BY ID_RECP_PREST DESC"
    ,nativeQuery = true)
    List<RecepPrest> findPrestacionByIdCliente(@Param(value = "id") Integer id,Pageable p);


    //Formato usado:  '2020-07-24 20:00:00
    @Query(value = "SELECT *  FROM RECEPCIÓN_PRESTACIÓN "+
    "where ID_RECP_PREST NOT in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+
    "WHERE ESTADO=0) "+ 
    "and  ID_RECP_PREST " + 
    "in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+ 
    "WHERE ESTADO=3) "+ 
    "and  ID_RECP_PREST "+ 
    "in (SELECT ID_RECEP_PREST FROM ESTADO_RECEP_PREST "+
    "WHERE ESTADO=5  and FECHA >= :fechai and FECHA <= :fechaf) "+ 
    "ORDER BY ID_RECP_PREST DESC",nativeQuery = true)
    List<RecepPrest> findPrestacionByFechas(@Param(value = "fechai") String fechai, @Param(value = "fechaf") String fechaf,Pageable p);

    @Query(value = "SELECT * FROM RECEPCIÓN_PRESTACIÓN "+ 
    "WHERE ID_RECP_PREST "+ 
    "IN (SELECT ID_RECEP_PREST  FROM ESTADO_RECEP_PREST "+ 
    "WHERE ESTADO=5 and FECHA >= :fechai and FECHA <= :fechaf) "+
    "ORDER BY ID_RECP_PREST DESC",nativeQuery = true)
    List<RecepPrest> findByFechas(@Param(value = "fechai") String fechai, @Param(value = "fechaf") String fechaf,Pageable p);
}
