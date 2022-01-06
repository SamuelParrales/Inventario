package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Empleado;
@Repository
public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Integer> {
	public Empleado findByCi(String ci);
	public Empleado findByCorreo(String correo);
	public Empleado findByNombres(String nombres);
	public List<Empleado> findAllByEstado(int estado); //metodo para el borrado logico

	//Paginación
	public List<Empleado> findAllByEstado(int estado,Pageable p);
}
