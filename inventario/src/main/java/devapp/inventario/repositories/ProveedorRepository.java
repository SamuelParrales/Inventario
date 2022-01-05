package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Proveedor;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor,Integer> {
    public Iterable<Proveedor> findAllByEstado(int estado); //metodo para el borrado logico
}
