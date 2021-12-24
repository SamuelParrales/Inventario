package devapp.inventario.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Recepciondespacho;

public interface RecepcionDespachoRepo extends CrudRepository<Recepciondespacho, Integer>{
	Set<Recepciondespacho> findBycliente(Cliente cliente);
}
