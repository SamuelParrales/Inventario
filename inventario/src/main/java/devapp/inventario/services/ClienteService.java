package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Cliente;
import devapp.inventario.repositories.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;
	
	public ArrayList<Cliente> getClientes(){
		return (ArrayList<Cliente>) clienteRepository.findAll();
	}
	
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Cliente getById(int id) throws Exception{
		return clienteRepository.findById(id).orElseThrow(()-> new Exception("Usuario no encontrado"));
	}
	
	public boolean checkCorreo(Cliente cliente) throws Exception {
		Optional<Cliente> clienteFound = clienteRepository.findByCorreo(cliente.getCorreo());
		if(clienteFound.isPresent()) {
			throw new Exception("Ya existe una cuenta con este correo vinculado");
			
		}
		return true;
	}
	
	public Cliente updateCliente(Cliente fromCliente) throws Exception{
		Cliente toCliente = getById(fromCliente.getId());
		mapCliente(fromCliente,toCliente);
		clienteRepository.save(toCliente);
		return null;
		
	}
	
	protected void mapCliente(Cliente from, Cliente to) {
		to.setCi(from.getCi());
		to.setNombres(from.getNombres());
		to.setApellidos(from.getApellidos());
		to.setPassword(from.getPassword());
	}
	
	public Cliente deleteClienteLogico(Cliente fromCliente) throws Exception {
		Cliente toCliente = getById(fromCliente.getId());
		changeEstado(fromCliente,toCliente);
		clienteRepository.save(toCliente);
		return null;
	}
	
	protected void changeEstado(Cliente from, Cliente to) {
		to.setEstado(from.getEstado());
	}
	
	/*public Cliente createCliente(Cliente cliente) throws Exception{ //VERIFICAR si existe una cuenta con ese correo para crear cliente
		if(checkCorreo(cliente)) {
			cliente = clienteRepository.save(cliente);
		}
		return cliente;
	}*/
	
	/*public boolean deleteCliente(int id) { //como borrar logicamente
		try {
			clienteRepository.deleteById(id);
			return true;
		}catch(Exception err){
			return false;
		}
	}*/

}
