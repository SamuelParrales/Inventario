package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import devapp.inventario.entities.Empleado;
import devapp.inventario.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {
	@Autowired
	EmpleadoRepository empleadoRepository;
	
	public ArrayList<Empleado> getEmpleados(){
		return (ArrayList<Empleado>) empleadoRepository.findAll();
	}
	
	public Empleado saveEmpleado(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}
	
	public Empleado getById(int id) throws Exception{
		return empleadoRepository.findById(id).orElseThrow(()-> new Exception("Usuario no encontrado"));
	}
	
	/*public boolean deleteEmpleado(int id) {
		try {
			empleadoRepository.deleteById(id);
			return true;
		}catch(Exception err){
			return false;
		}
	}*/
	
	public boolean checkCorreo(Empleado empleado) throws Exception {
		Optional<Empleado> empleadoFound = empleadoRepository.findByCorreo(empleado.getCorreo());
		if(empleadoFound.isPresent()) {
			throw new Exception("Ya existe una cuenta con este correo vinculado");
		}
		return true;
	}
	
	public Empleado updateEmpleado(Empleado fromEmpleado) throws Exception{
		Empleado toEmpleado = getById(fromEmpleado.getId());
		mapEmpleado(fromEmpleado,toEmpleado);
		empleadoRepository.save(toEmpleado);
		return null;
		
	}
	
	protected void mapEmpleado(Empleado from, Empleado to) {
		to.setCi(from.getCi());
		to.setTipo(from.getTipo());
		to.setNombres(from.getNombres());
		to.setApellidos(from.getApellidos());
		to.setPassword(from.getPassword());
	}
	
	public Empleado createEmpleado(Empleado empleado) throws Exception{ //VERIFICAR si existe una cuenta con ese correo para crear empleado
		if(checkCorreo(empleado)) {
			empleado = empleadoRepository.save(empleado);
		}
		return empleado;
	}

	public Empleado deleteEmpleadoLogico(Empleado fromEmpleado) throws Exception {
		Empleado toEmpleado = getById(fromEmpleado.getId());
		changeEstado(fromEmpleado,toEmpleado);
		empleadoRepository.save(toEmpleado);
		return null;
	}

	protected void changeEstado(Empleado from, Empleado to) {
		to.setEstado(from.getEstado());
	}

}
