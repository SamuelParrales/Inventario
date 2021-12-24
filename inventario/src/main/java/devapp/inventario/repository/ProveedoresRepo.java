package devapp.inventario.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;


import devapp.inventario.entities.Proveedores;

public interface ProveedoresRepo extends CrudRepository<Proveedores, Integer> {
	Set<Proveedores> findBynombre(String nombre);
}
