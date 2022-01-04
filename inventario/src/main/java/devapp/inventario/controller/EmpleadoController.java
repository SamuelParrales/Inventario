package devapp.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.dto.PrestacionEmplDto;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.services.EmpleadoService;
import devapp.inventario.services.RecepPrestService;
import devapp.inventario.services.RecordNotFoundException;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	@Autowired
	EmpleadoService service;

	@Autowired
	RecepPrestService recepPrestService;
	
	@GetMapping("/get/all")
	public ResponseEntity<List<Empleado>> getAll() {
		List<Empleado> list = service.getAll();
		return new ResponseEntity<List<Empleado>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Empleado> getEmpleadoById(@PathVariable("id") int id) throws RecordNotFoundException {
		Empleado entity = service.findById(id);
		return new ResponseEntity<Empleado>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado){
		service.createEmpleado(empleado);
		return new ResponseEntity<Empleado>(empleado, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Empleado> updateEmpleado(@RequestBody Empleado empleado) throws RecordNotFoundException{
		service.updateEmpleado(empleado);
		return new ResponseEntity<Empleado>(empleado, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteEmpleadoById(@PathVariable("id") int id) throws RecordNotFoundException {
		service.deleteEmpleadoById(id);
		return HttpStatus.OK;
	}

	///*************Reservaciones de los empleados */

	@PostMapping(path = "/{idE}/prestacion/")
    public RecepPrest savePrestacion(@PathVariable("idE") int idEmpleado,
        @RequestBody PrestacionEmplDto save)
    {
    
        return recepPrestService.savePrestacion(save,idEmpleado);
    }
	//Para modificar el estado de la reservacion
	@PutMapping(path ="/{idE}/reservacion/{idR}")
    public RecepPrest updateReservacion( @PathVariable("idE") int idEmpleado,
    @PathVariable("idR") long idR,
	@RequestParam(required = false,defaultValue = "update") String action,
	@RequestBody(required = false) PrestacionEmplDto prestacionDto)
    {
		if(action.equals("cancel"))
			return recepPrestService.cancelReservacionEmpl(idR, idEmpleado);

		if(action.equals("update"))
		{
			if(prestacionDto!=null)
			return recepPrestService.actualizarReservacionEmpl(prestacionDto, idR,idEmpleado);
		}
		return null;
    }



	// @PutMapping(path ="/{idE}/reservacion/{idR}")
	// public RecepPrest cancelReservacion
	// (

	// )

	
}				

