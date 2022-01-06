package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Empleado;
@Repository
public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Integer> {
	//Consultas jpql
	@Query(value = "select * from empleado e where "+ 
	"(UPPER(e.nombres) like UPPER(concat('%',:palabra,'%')) "+ 
	"or UPPER(e.apellidos) like UPPER(concat('%',:palabra,'%'))) "+ 
	"and estado=1",nativeQuery = true)
	public List<Empleado> filterByNombresOrApellidos(@Param("palabra") String palabra,Pageable p);
	
	public Empleado findByNombres(String nombres);
	public Empleado findByCi(String ci);
	public Empleado findByCorreo(String correo);
	public List<Empleado> findAllByEstado(int estado); //metodo para el borrado logico

	//Paginación
	public List<Empleado> findAllByEstado(int estado,Pageable p);
}
