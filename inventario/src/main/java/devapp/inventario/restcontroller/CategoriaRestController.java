package devapp.inventario.restcontroller;

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

import devapp.inventario.entities.Categoria;
import devapp.inventario.services.CategoriaService;
import devapp.inventario.services.RecordNotFoundException;


@RestController
@RequestMapping("/api/v1categoria")
public class CategoriaRestController {
	@Autowired
	CategoriaService service;
	
	@GetMapping("/get/all")
	public ResponseEntity<List<Categoria>> getAll() {
		List<Categoria> list = service.getAll();
		return new ResponseEntity<List<Categoria>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Categoria> getCategoriaByIdCategoria(@PathVariable("id") int idCategoria) throws RecordNotFoundException {
		Categoria entity = service.findByIdCategoria(idCategoria);
		return new ResponseEntity<Categoria>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria){
		service.createCategoria(categoria);
		return new ResponseEntity<Categoria>(categoria, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Categoria> updateCategoria(@RequestBody Categoria categoria) throws RecordNotFoundException{
		service.updateCategoria(categoria);
		return new ResponseEntity<Categoria>(categoria, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteCategoriaByIdCategoria(@PathVariable("id") int idCategoria) throws RecordNotFoundException {
		service.deleteCategoriaByIdCategoria(idCategoria);
		return HttpStatus.OK;
	}
}				
