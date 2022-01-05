package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.DetRecepPrest;

@Repository
public interface DetRecepPrestRepository extends CrudRepository<DetRecepPrest,Long> {
    
}
