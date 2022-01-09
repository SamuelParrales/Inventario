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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.dto.ProductoDto;
import devapp.inventario.entities.Producto;
import devapp.inventario.services.ProductoService;
import devapp.inventario.services.RecordNotFoundException;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	@Autowired
	ProductoService service;
	
	@GetMapping("/all")
	public ResponseEntity<List<Producto>> getAll() {
		List<Producto> list = service.getAll();
		return new ResponseEntity<List<Producto>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProductoById(@PathVariable("id") int id) throws RecordNotFoundException {
		Producto entity = service.findById(id);
		return new ResponseEntity<Producto>(entity, new HttpHeaders(), HttpStatus.OK);
	}
    
	@PostMapping("/create")
	public ResponseEntity<Producto> createProducto(@RequestBody ProductoDto producto){
		Producto productTemp = new Producto();
		productTemp = service.createProducto(producto);
		return new ResponseEntity<Producto>(productTemp,new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) throws RecordNotFoundException{
		service.updateProducto(producto);
		return new ResponseEntity<Producto>(producto, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteProductoById(@PathVariable("id") int id) throws RecordNotFoundException {
		service.deleteProductoById(id);
		return HttpStatus.OK;
	}

	@GetMapping("/state")
	public ResponseEntity<Iterable<Producto>> getStateActiveProducts(@RequestParam("estado") int estado){
		Iterable<Producto> StateActive =service.DeleteLogic(estado);
		return new ResponseEntity<Iterable<Producto>>(StateActive, new HttpHeaders(), HttpStatus.OK);
	}

}				

