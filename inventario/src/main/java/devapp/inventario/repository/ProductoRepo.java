package devapp.inventario.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Producto;




public interface ProductoRepo extends CrudRepository<Producto, Integer> {
	Set<Producto> findBynombre(String nombre);
}
