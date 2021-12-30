package devapp.inventario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
	public Optional<Empleado> findByCorreo(String correo);
}
