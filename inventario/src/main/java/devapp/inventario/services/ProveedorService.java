package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Proveedor;
import devapp.inventario.repositories.ProveedorRepository;

@Service
public class ProveedorService {

	@Autowired
	ProveedorRepository repo;

	public List<Proveedor> getAll(){
		List<Proveedor> proveedorList = (List<Proveedor>) repo.findAll();
		if(proveedorList.size() > 0) {
			return proveedorList;
		} else {
			return new ArrayList<Proveedor>();
		}
	}
     		
	public Proveedor findById(int id) throws RecordNotFoundException{
		Optional<Proveedor> proveedor = repo.findById(id);
		if(proveedor.isPresent()) {
			return proveedor.get();
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public Proveedor createProveedor(Proveedor proveedor){
		return repo.save(proveedor);
	}

	public Proveedor updateProveedor(Proveedor proveedor) throws RecordNotFoundException {
		Optional<Proveedor> proveedorTemp = repo.findById(proveedor.getId());
	
		if(proveedorTemp.isPresent()){
			return repo.save(proveedor);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public void deleteProveedorById(int id) throws RecordNotFoundException{
		Optional<Proveedor> proveedor = repo.findById(id);
		if(proveedor.isPresent()) {
		repo.deleteById(id);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}	
	
	public Iterable<Proveedor> DeleteLogic(int estado){
		Iterable<Proveedor> StateActive= repo.findAllByEstado(estado);
		return StateActive; 
	}

}
