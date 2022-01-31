package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Cliente;
import devapp.inventario.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repo;

	public List<Cliente> getAll(){
		List<Cliente> clienteList = (List<Cliente>) repo.findAll();
		if(clienteList.size() > 0) {
			return clienteList;
		} else {
			return new ArrayList<Cliente>();
		}
	}
     		
	public Cliente findById(int id) throws RecordNotFoundException{
		Optional<Cliente> cliente = repo.findById(id);
		if(cliente.isPresent()) {
			return cliente.get();
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public Cliente createCliente(Cliente cliente){
		return repo.save(cliente);
	}

	public Cliente updateCliente(Cliente cliente) throws RecordNotFoundException {
		Optional<Cliente> clienteTemp = repo.findById(cliente.getId());
	
		if(clienteTemp.isPresent()){
			return repo.save(cliente);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public void deleteClienteById(int id) throws RecordNotFoundException{
		Optional<Cliente> cliente = repo.findById(id);
		if(cliente.isPresent()) {
		repo.deleteById(id);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}	
	
	public Iterable<Cliente> DeleteLogic(int estado){
		Iterable<Cliente> StateActive= repo.findAllByEstado(estado);
		return StateActive; 
	}

	public Cliente getByCi(String ci)
	{
		return repo.findByCi(ci);
	}

	public Cliente getByCorreo(String email)
	{
		return repo.findByCorreo(email);
	}

}