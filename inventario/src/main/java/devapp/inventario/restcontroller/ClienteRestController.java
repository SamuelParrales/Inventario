package devapp.inventario.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.dto.ReservacionClientDto;
import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.RecepPrest;

import devapp.inventario.services.ClienteService;
import devapp.inventario.services.RecepPrestService;
import devapp.inventario.services.RecordNotFoundException;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteRestController {
	@Autowired
	ClienteService service;
	
	@Autowired
    RecepPrestService recepPrestService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Cliente>> getAll() {
		List<Cliente> list = service.getAll();
		return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(
		@PathVariable("id") int id) throws RecordNotFoundException {
		Cliente entity = service.findById(id);
		return new ResponseEntity<Cliente>(entity, new HttpHeaders(), HttpStatus.OK);
	}
	@GetMapping("/ci/{ci}")
	public ResponseEntity<Cliente> getClienteCi(@PathVariable("ci") String ci)
	{
		Cliente entity = service.getByCi(ci);
		return new ResponseEntity<Cliente>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
		service.createCliente(cliente);
		return new ResponseEntity<Cliente>(cliente, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) throws RecordNotFoundException{
		service.updateCliente(cliente);
		return new ResponseEntity<Cliente>(cliente, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteClienteById(@PathVariable("id") int id) throws RecordNotFoundException {
		service.deleteClienteById(id);
		return HttpStatus.OK;
	}


	/***************Para las reservaciones */

	@PostMapping(path = "/reservacion")
    public ResponseEntity<RecepPrest> saveReservacion(
        @RequestBody ReservacionClientDto save) throws RecordNotFoundException
    {
		int idCliente;
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
			}
			String email = userDetails.getUsername();
			idCliente = service.getByCorreo(email).getId();
			
		}
		catch(Exception e)
		{
			return null;
		}


		RecepPrest recepPrest = recepPrestService.saveReservacionCliente(save,idCliente);
		if(recepPrest!=null)
            return new ResponseEntity<RecepPrest>(recepPrest, new HttpHeaders(), HttpStatus.OK);
        else
        return new ResponseEntity<RecepPrest>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping(path =  "/{idC}/reservacion/{idR}")
    public RecepPrest updateClientReservacion(@PathVariable("idC") int idCliente,
    @PathVariable("idR") Long idReser,
	@RequestParam(required = false,defaultValue = "update") String action,
    @RequestBody(required = false) ReservacionClientDto reservacionDto)
    {
		System.out.println(action);
		if(action.equals("cancel"))
			return recepPrestService.cancelReservacionClient(idReser, idCliente);

		if(action.equals("update"))
		{
			if(reservacionDto!=null)
				return recepPrestService.actualizarReservacionClient(reservacionDto, idReser,idCliente);
		}
		
		return null;
    }

	@GetMapping("/state")
	public ResponseEntity<Iterable<Cliente>> getStateActiveProducts(@RequestParam("estado") int estado){
		Iterable<Cliente> StateActive =service.DeleteLogic(estado);
		return new ResponseEntity<Iterable<Cliente>>(StateActive, new HttpHeaders(), HttpStatus.OK);
	}
	
}				
