package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor,Integer> {
    
}
