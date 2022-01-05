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

import devapp.inventario.entities.Proveedor;
import devapp.inventario.services.ProveedorService;
import devapp.inventario.services.RecordNotFoundException;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
	@Autowired
	ProveedorService service;
	
	@GetMapping("/get/all")
	public ResponseEntity<List<Proveedor>> getAll() {
		List<Proveedor> list = service.getAll();
		return new ResponseEntity<List<Proveedor>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Proveedor> getProveedorById(@PathVariable("id") int id) throws RecordNotFoundException {
		Proveedor entity = service.findById(id);
		return new ResponseEntity<Proveedor>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Proveedor> createProveedor(@RequestBody Proveedor proveedor){
		service.createProveedor(proveedor);
		return new ResponseEntity<Proveedor>(proveedor, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Proveedor> updateProveedor(@RequestBody Proveedor proveedor) throws RecordNotFoundException{
		service.updateProveedor(proveedor);
		return new ResponseEntity<Proveedor>(proveedor, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteProveedorById(@PathVariable("id") int id) throws RecordNotFoundException {
		service.deleteProveedorById(id);
		return HttpStatus.OK;
	}
}				
