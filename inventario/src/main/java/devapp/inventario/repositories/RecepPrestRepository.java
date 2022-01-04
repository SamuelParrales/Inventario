package devapp.inventario.repositories;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.RecepPrest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepPrestRepository extends  CrudRepository<RecepPrest,Long> 
{
    RecepPrest findByIdAndCliente(Long id, Cliente cliente);
    
}
