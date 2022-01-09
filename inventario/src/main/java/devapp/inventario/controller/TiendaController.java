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

    @GetMapping()
	public String tienda(Model model) 
	{
		
		model.addAttribute("productos",productoService.paginacion(1)); 
		model.addAttribute("cantPagina", productoService.cantPaginacion("all"));
		model.addAttribute("categorias", categoriaService.getAll().listIterator(1));
		model.addAttribute("pageActive", 1);
		return "tienda";
	}

	@GetMapping("/page/{pag}")
	public String tiendaPag(
	@PathVariable("pag") int pag, 
	@RequestParam(required = false,defaultValue = "all") String categoria ,
	Model model) 
	{
		model.addAttribute("cantPagina", productoService.cantPaginacion(categoria));
		model.addAttribute("pageActive", pag);
		model.addAttribute("categorias", categoriaService.getAll().listIterator(1));
		if(categoria.equals("all"))
		{
			
			
			model.addAttribute("productos",productoService.paginacion(pag));
			return "tienda.html";
		}
			
		
		model.addAttribute("productos",productoService.paginacion(pag,categoria));
		
		return "tienda.html";
	}
}
