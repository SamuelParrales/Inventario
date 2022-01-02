package devapp.inventario.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import devapp.inventario.entities.Categoria;
import devapp.inventario.repositories.CategoriaRepository;

class CategoriaServiceTest {
	//servicio
	CategoriaService service;

	//reopositorio mockeado
	@Mock
	CategoriaRepository categoriaRepository;
	
	//referencia a la entidad
	Categoria categoria;

	//definiendo un array optional
	Optional<Categoria> optional;

	//ArrayList
	ArrayList<Categoria> categorias= new ArrayList<Categoria>();

	@BeforeEach
	void setUp() throws Exception {
		categoriaRepository= Mockito.mock(CategoriaRepository.class);
		service =new CategoriaService(categoriaRepository);
		//preparando la clase -- simulacion del llenado de base de datos
		categoria = new Categoria(1, "vasos", "esto es un vaso");
		//incializando el arraylist
		categorias.add(categoria);
		categorias.add(new Categoria(2,"silla","esto es una silla"));
		//inicializando el array optional 
		optional = Optional.of(categoria);

		
		//definiendo el comportamiento saveCategorias   
		when(this.categoriaRepository.save(categoria)).thenReturn(categoria);
		//definiendo el comportamiento GetCategorias
		when(this.categoriaRepository.findAll()).thenReturn(categorias);
		//definiendo el comportamiento de GetById
		when(this.categoriaRepository.findById(1)).thenReturn(optional);
		//definienfo el comportamiento encontrar por nombres
		when(this.categoriaRepository.findByNombre("vasos")).thenReturn(optional);

		
		
	}

	@Test
	void getCategoriastest(){
		//tomando la categoria esperada
		ArrayList<Categoria> categoriasTemp = this.service.getCategorias();
		boolean comparacion = categoriasTemp.equals(this.categorias);
		Assertions.assertEquals(true, comparacion);

	}


	/*@Test
	void saveCategoriastest() {
		Categoria Temp = this.service.saveCategoria(categoria); //llamando al metodo implementado en services
		Assertions.assertEquals(categoria.getId(), Temp.getId()); //comprobando los id
		Assertions.assertEquals(categoria.getDescripcion(), Temp.getDescripcion()); //comprobando la descripcion 
		Assertions.assertEquals(categoria.getNombre(), Temp.getNombre()); // comprobando el nombre
	}*/

	@Test
	void getByIdtest(){
		Categoria categoriatemp = new Categoria();
		try {
			categoriatemp = this.service.getById(1); //compruebo la existencia 
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean equals = categoriatemp.equals(optional.get());
		Assertions.assertEquals(true,equals);
	}

	@Test
	void deleteCategoriatest(){
		//test unitario sin mock
		boolean Catecomparation = categorias.contains(categoria);
		if (Catecomparation) this.categoriaRepository.deleteById(categoria.getId());
		else Catecomparation = false;
		assertEquals(true, Catecomparation);
	}

	@Test
	void checkNombretest(){
		//String nombre = categorias.get(0).getNombre(); //nombre en un escenario existente
		String nombreTest = "this.is.the.test";
		Optional<Categoria> OptionalTemp= this.categoriaRepository.findByNombre(nombreTest); 
		boolean result = optional.equals(OptionalTemp);
		assertEquals(false, result);
	}

}
