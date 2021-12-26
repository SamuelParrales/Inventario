package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer> 
{
    
}
