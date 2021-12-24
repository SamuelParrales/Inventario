package devapp.inventario.repository;


import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Cliente;
public interface ClienteRepo extends CrudRepository<Cliente, Integer>{
	 Set<Cliente> findBynombre(String nombre);
}

