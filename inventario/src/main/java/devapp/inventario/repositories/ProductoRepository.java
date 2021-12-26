package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Producto;

public interface ProductoRepository  extends CrudRepository<Producto,Integer>{
    
}
