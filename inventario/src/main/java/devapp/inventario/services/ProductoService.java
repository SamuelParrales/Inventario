package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.dto.ProductoDto;
import devapp.inventario.entities.Categoria;
import devapp.inventario.entities.Producto;
import devapp.inventario.entities.Proveedor;
import devapp.inventario.repositories.CategoriaRepository;
import devapp.inventario.repositories.ProductoRepository;
import devapp.inventario.repositories.ProveedorRepository;

@Service
public class ProductoService {

	@Autowired
	ProductoRepository repo;

	@Autowired
	ProveedorRepository proveedorRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	public List<Producto> getAll(){
		List<Producto> productoList = (List<Producto>) repo.findAll();
		if(productoList.size() > 0) {
			return productoList;
		} else {
			return new ArrayList<Producto>();
		}
	}
     		
	public Producto findById(int id) throws RecordNotFoundException{
		Optional<Producto> producto = repo.findById(id);
		if(producto.isPresent()) {
			return producto.get();
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public Producto createProducto(ProductoDto producto){
		Producto producTemp = this.mapearProducto(producto);
		return repo.save(producTemp);
	}

	public Producto updateProducto(Producto producto) throws RecordNotFoundException {
		Optional<Producto> productoTemp = repo.findById(producto.getId());
	
		if(productoTemp.isPresent()){
			return repo.save(producto);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public void deleteProductoById(int id) throws RecordNotFoundException{
		Optional<Producto> producto = repo.findById(id);
		if(producto.isPresent()) {
		repo.deleteById(id);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	private Producto mapearProducto(ProductoDto producto){
		Producto producTemp = new Producto();
		producTemp.setCantDisponible(producto.getCantDisponible());
		producTemp.setCantPrestada(0);
		producTemp.setNombre(producto.getNombre());
		producTemp.setDescripcion(producto.getDescripcion());
		producTemp.setValorPrestacion(producto.getValorPrestacion());
		producTemp.setValorUnitario(producto.getValorUnitario());
		producTemp.setEstado(producto.getEstado());
		producTemp.setProveedores(this.guardarProveedor(producto));
		producTemp.setCategoria(this.guardarCategoria(producto));
		producTemp.setEstado(producto.getEstado());
		return producTemp;
	}

	private List<Proveedor> guardarProveedor(ProductoDto producto){
		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		List<Integer> idProvedores = producto.getIdProveedores();
		for (Integer i : idProvedores) {
			Optional<Proveedor> proveedor = Optional.empty();
			proveedor = proveedorRepository.findById(i);
			if (proveedor.isPresent()){
				Proveedor prov = new Proveedor();
				prov = proveedor.get();
				proveedores.add(prov);
			}
		
		}
		return proveedores;
	} 

	private Categoria guardarCategoria (ProductoDto producto){
		Optional <Categoria> categoriaTemp= Optional.empty();
		Categoria categoria = new Categoria();
		categoria = null;
		categoriaTemp=categoriaRepository.findById(producto.getIdCategoria());
		if (categoriaTemp.isPresent()){
			categoria = categoriaTemp.get();
		}
		return categoria;
	}

	public Iterable<Producto> DeleteLogic(int estado){
		Iterable<Producto> StateActive= repo.findAllByEstado(estado);
		return StateActive; 
	}

}