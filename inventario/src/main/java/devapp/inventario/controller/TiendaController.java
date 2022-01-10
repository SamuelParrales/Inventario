package devapp.inventario.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import devapp.inventario.services.CategoriaService;
import devapp.inventario.services.ProductoService;

@Controller
@RequestMapping("/tienda")
public class TiendaController 
{
    @Autowired
	private ProductoService productoService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(value =  {"/page/{pag}"})
	public String tiendaPag(
	@PathVariable("pag") int pag, 
	@RequestParam(required = false,defaultValue = "all") String categoria,
	@RequestParam(required = false,defaultValue = "") String search,
	Model model) 
	{
	
		model.addAttribute("pageActive", pag); //Envia el numero de la pagina actual
		model.addAttribute("categorias", categoriaService.getAll().listIterator(1));
		model.addAttribute("cantPagina", productoService.cantPag(categoria,search));
		model.addAttribute("search", search);
		if(search.equals(""))
			model.addAttribute("productos",productoService.pag(pag,categoria));
		else
			model.addAttribute("productos",productoService.pag(pag,categoria,search));
			
		return "tienda.html";
	}
}
