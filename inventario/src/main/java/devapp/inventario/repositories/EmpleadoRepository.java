package devapp.inventario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Empleado;
@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
	public Optional<Empleado> findByCorreo(String correo);
	public Empleado findByNombres(String nombres);
}
