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
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.entities.Cliente;
import devapp.inventario.services.ClienteService;
import devapp.inventario.services.RecordNotFoundException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired
	ClienteService service;
	
	@GetMapping("/get/all")
	public ResponseEntity<List<Cliente>> getAll() {
		List<Cliente> list = service.getAll();
		return new ResponseEntity<List<Cliente>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable("id") int id) throws RecordNotFoundException {
		Cliente entity = service.findById(id);
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
}				
