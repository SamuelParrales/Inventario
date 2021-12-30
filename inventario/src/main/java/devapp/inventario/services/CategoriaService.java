package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Categoria;
import devapp.inventario.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	CategoriaRepository categoriaRepository;
	
	public ArrayList<Categoria> getCategorias(){
		return (ArrayList<Categoria>) categoriaRepository.findAll();
	}
	
	public Categoria saveCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria getById(int id) throws Exception{
		return categoriaRepository.findById(id).orElseThrow(()-> new Exception("Categoria no encontrada"));
	}
	
	public boolean deleteCategoria(int id) {
		try {
			categoriaRepository.deleteById(id);
			return true;
		}catch(Exception err){
			return false;
		}
	}
	
	public boolean checkNombre(Categoria categoria) throws Exception {
		Optional<Categoria> categoriaFound = categoriaRepository.findByNombre(categoria.getNombre());
		if(categoriaFound.isPresent()) {
			throw new Exception("Ya existe una categoria con ese nombre");
		}
		return true;
	}
	
	public Categoria updateCategoria(Categoria fromCategoria) throws Exception{
		Categoria toCategoria = getById(fromCategoria.getId());
		mapCategoria(fromCategoria,toCategoria);
		categoriaRepository.save(toCategoria);
		return null;
		
	}
	
	protected void mapCategoria(Categoria from, Categoria to) {
		to.setNombre(from.getNombre());
		to.setDescripcion(from.getDescripcion());
	}
	
	/*public Categoria createCategoria(Categoria categoria) throws Exception{ //VERIFICAR si existe una categoria con ese nombre para crear categoria
		if(checkNombre(categoria)) {
			categoria = categoriaRepository.save(categoria);
		}
		return categoria;
	}*/

}
