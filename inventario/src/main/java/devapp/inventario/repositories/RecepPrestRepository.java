package devapp.inventario.repositories;

import devapp.inventario.entities.RecepPrest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepPrestRepository extends  CrudRepository<RecepPrest,Long> {

    void deleteById(int id);
    
}
