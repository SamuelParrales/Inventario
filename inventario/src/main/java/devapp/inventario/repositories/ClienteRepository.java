package devapp.inventario.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import devapp.inventario.entities.Cliente;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente,Integer> 
{
    public Cliente findByCi(String ci);
    public Cliente findByCorreo(String correo);
    public List<Cliente> findAllByEstado(int estado); //metodo para el borrado logico

    public List<Cliente> findAllByEstado(int estado,Pageable p);
}
