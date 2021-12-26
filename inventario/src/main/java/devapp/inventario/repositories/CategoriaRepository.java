package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria,Integer>{
    
}
