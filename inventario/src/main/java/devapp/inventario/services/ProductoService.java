package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	private ProductoRepository repo;

	@Autowired
	private ProveedorRepository proveedorRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	private int tamPagina =  12;
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

	public int cantPag(String nombreCateg,String search)	//Permite obtener la cantidad de paginas
	{
		
		long cantInstancias;		//Cantidad de filas
		if(nombreCateg.equals("all"))
		{
			if(search.equals(""))	//Sino envió nada para buscar
				cantInstancias= repo.countByEstado(1);//Muestra todos los que estan activo
			else
				cantInstancias= repo.countFilterByNombre(search);	//Caso contrario filtra por lo que se esta buscando
		}
		else
		{
			Categoria categoria = categoriaRepository.findByNombre(nombreCateg);
			if(search.equals("")) //Sino envió nada para buscar
				cantInstancias= repo.countByEstadoAndCategoria(1, categoria); //Muestra todo de acuerdo con la categoria
			else
				cantInstancias= repo.countFilterByNombreFindCategoria_Id(search, categoria.getId()); //Muestra todo de aceurdo con la palabra search y la categoria

		}


		double cantPagina = ((double) cantInstancias/(double) tamPagina);
		int cantPaginaRex = (int) Math.ceil(cantPagina);
		if(cantPaginaRex<cantPagina)
			return cantPaginaRex+1;

		return cantPaginaRex;
	}


	public List<Producto> pag(int n, String categoria)
	{
		n=n-1;
		Pageable p = PageRequest.of(n, tamPagina);

		if(categoria.equals("all"))
			return repo.findAllByEstado(1,p);
		
		return repo.findAllByEstadoAndCategoria_nombre(1, categoria,p);
	}
	public List<Producto> pag(int n, String nombrecateg,String search)
	{

		n=n-1;
		Pageable p = PageRequest.of(n, tamPagina);
		Categoria categoria = categoriaRepository.findByNombre(nombrecateg);
		if(nombrecateg.equals("all"))
			return repo.filterByNombre(search, p);

		return repo.filterByNombreFindCategoria_Id(search, categoria.getId(), p);
	}

	public List<Producto> searchN(String search,int n)
	{
		if(n<1)
			return null;
		
		Pageable p = PageRequest.of(0, n);
		return repo.filterByNombre(search, p);
	}
}