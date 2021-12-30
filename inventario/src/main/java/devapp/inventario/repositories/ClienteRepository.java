package devapp.inventario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer> 
{
    public Optional<Cliente> findByCorreo(String correo);
}
