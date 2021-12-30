package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Producto;

@Repository
public interface ProductoRepository  extends CrudRepository<Producto,Integer>{
    
}
