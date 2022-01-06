package devapp.inventario.repositories;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.RecepPrest;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
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
       
}
