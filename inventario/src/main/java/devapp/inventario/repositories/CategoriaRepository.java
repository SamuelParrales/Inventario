package devapp.inventario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria,Integer>{
    public Optional<Categoria> findByNombre(String nombre);
}
