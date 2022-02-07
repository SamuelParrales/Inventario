package devapp.inventario.repositories;



import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Categoria;

@Repository
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria,Integer>{
    public Categoria findByNombre(String nombre);

}
