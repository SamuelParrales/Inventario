package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Categoria;
import devapp.inventario.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	CategoriaRepository repo;

	public List<Categoria> getAll(){
		List<Categoria> categoriaList = (List<Categoria>) repo.findAll();
		if(categoriaList.size() > 0) {
			return categoriaList;
		} else {
			return new ArrayList<Categoria>();
		}
	}
     		
	public Categoria findByIdCategoria(int idCategoria) throws RecordNotFoundException{
		Optional<Categoria> categoria = repo.findById(idCategoria);
		if(categoria.isPresent()) {
			return categoria.get();
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public Categoria createCategoria(Categoria categoria){
		return repo.save(categoria);
	}

	public Categoria updateCategoria(Categoria categoria) throws RecordNotFoundException {
		Optional<Categoria> categoriaTemp = repo.findById(categoria.getId());
	
		if(categoriaTemp.isPresent()){
			return repo.save(categoria);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}

	public void deleteCategoriaByIdCategoria(int idCategoria) throws RecordNotFoundException{
		Optional<Categoria> categoria = repo.findById(idCategoria);
		if(categoria.isPresent()) {
		repo.deleteById(idCategoria);
		} else {
			throw new RecordNotFoundException("Record does not exist for the given Id");
		}
	}	

}
