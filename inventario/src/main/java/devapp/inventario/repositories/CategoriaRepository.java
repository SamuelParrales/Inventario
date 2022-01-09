package devapp.inventario.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria,Integer>{
    public Categoria findByNombre(String nombre);
}
