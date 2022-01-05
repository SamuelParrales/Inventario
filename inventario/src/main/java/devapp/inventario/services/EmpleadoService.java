package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import devapp.inventario.entities.Empleado;
import devapp.inventario.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {

	@Autowired
	EmpleadoRepository repo;

	public List<Empleado> getAll(){
		List<Empleado> empleadoList = (List<Empleado>) repo.findAll();
		if(empleadoList.size() > 0) {
			return empleadoList;
		} else {
			return new ArrayList<Empleado>();
		}
	}
     		
	public Empleado findById(int id) throws RecordNotFoundException{
		Optional<Empleado> empleado = repo.findById(id);
		if(empleado.isPresent()) {
			return empleado.get();
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public Empleado createEmpleado(Empleado empleado){
		return repo.save(empleado);
	}

	public Empleado updateEmpleado(Empleado empleado) throws RecordNotFoundException {
		Optional<Empleado> empleadoTemp = repo.findById(empleado.getId());
	
		if(empleadoTemp.isPresent()){
			return repo.save(empleado);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public void deleteEmpleadoById(int id) throws RecordNotFoundException{
		Optional<Empleado> empleado = repo.findById(id);
		if(empleado.isPresent()) {
		repo.deleteById(id);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}
	
	public Iterable<Empleado> DeleteLogic(int estado){
		Iterable<Empleado> StateActive= repo.findAllByEstado(estado);
		return StateActive; 
	}

}

