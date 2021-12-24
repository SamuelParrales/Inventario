package devapp.inventario.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Categoria;


public interface CategoriaRepo extends CrudRepository<Categoria, Integer>{
	Set<Categoria> findBynombre(String nombre);
}
