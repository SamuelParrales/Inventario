package devapp.inventario.repository;

import org.springframework.data.repository.CrudRepository;
import devapp.inventario.entities.Detallerecdes;
import devapp.inventario.entities.Cliente;
public interface DetallerRecDesRepo extends CrudRepository<Detallerecdes, Integer>{

}
