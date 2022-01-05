package devapp.inventario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,Integer> 
{
    public Optional<Cliente> findByCorreo(String correo);
    public Iterable<Cliente> findAllByEstado(int estado); //metodo para el borrado logico

}
