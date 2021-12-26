package devapp.inventario.repositories;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado,Long> {
    
}
