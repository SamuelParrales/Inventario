package devapp.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import devapp.inventario.entities.Categoria;
import devapp.inventario.services.CategoriaService;


@Controller
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/categoriaForm")
    public String categoriaForm(Model model){
        model.addAttribute("categoriaForm", new Categoria());
        model.addAttribute("categoriaList", categoriaService.getCategorias());
        model.addAttribute("listTab", "active");
        return "categoria-form/user-view";
    }

    @PostMapping("/categoriaForm")
    public String createCategoria(@ModelAttribute("categoriaForm")Categoria categoria, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("categoriaForm", categoria);
            model.addAttribute("formTab", "active");
        }else{
            try {
                categoriaService.createCategoria(categoria);
                model.addAttribute("categoriaForm", new Categoria());
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("categoriaForm", categoria);
                model.addAttribute("formTab", "active");
            } 
        }
        model.addAttribute("categoriaList", categoriaService.getCategorias());
        return "categoria-form/user-view";
    }
    
    @GetMapping("/editCategoria/{id}")
    public String getEditCategoriaForm(Model model, @PathVariable(name = "id") int id)throws Exception{
        Categoria categoriaToEdit = categoriaService.getById(id);
        model.addAttribute("categoriaForm", categoriaToEdit);
        model.addAttribute("categoriaList", categoriaService.getCategorias());
        model.addAttribute("formTab", "active");
        model.addAttribute("editMode", "true");

        return "categoria-form/user-view";
    }

    @PostMapping("/editCategoria")
    public String postEditCategoriaForm(@ModelAttribute("categoriaForm")Categoria categoria, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("categoriaForm", categoria);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
        }else{
            try {
                categoriaService.updateCategoria(categoria);
                model.addAttribute("categoriaForm", categoria);
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("categoriaForm", categoria);
                model.addAttribute("formTab", "active");
                model.addAttribute("editMode", "true");
            } 
        }
        model.addAttribute("categoriaList", categoriaService.getCategorias());
        return "categoria-form/user-view";
    }

    @GetMapping("/editCategoria/cancel")
    public String cancelEditCategoria(ModelMap model){
        return "redirect:/categoriaForm";
    }

    @GetMapping("/deleteCategoria/{id}")
    public String deleteCategoria(Model model, @PathVariable(name="id")int id){
        try {
            categoriaService.deleteCategoria(id);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage", e.getMessage());
        }

        return categoriaForm(model);
    }
}
