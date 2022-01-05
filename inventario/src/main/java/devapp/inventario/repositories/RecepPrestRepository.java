package devapp.inventario.repositories;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.RecepPrest;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepPrestRepository extends  CrudRepository<RecepPrest,Long> 
{
    RecepPrest findByIdAndCliente(Long id, Cliente cliente);
    List<RecepPrest> findAllByClienteOrderByIdDesc(Cliente cliente);
    List<RecepPrest> findAllByEstados_EstadoOrderByIdDesc(int estado);
    Set<RecepPrest> findAllByEstados_EmpleadoOrderByIdDesc(Empleado empleado);
       
}
