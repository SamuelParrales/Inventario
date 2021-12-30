package devapp.inventario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria,Integer>{
    public Optional<Categoria> findByNombre(String nombre);
}
