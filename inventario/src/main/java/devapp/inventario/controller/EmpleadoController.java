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

import devapp.inventario.entities.Empleado;
import devapp.inventario.services.EmpleadoService;

@Controller
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/empleadoForm")
    public String clienteForm(Model model){
        model.addAttribute("empleadoForm", new Empleado());
        model.addAttribute("empleadoList", empleadoService.getEmpleados());
        model.addAttribute("listTab", "active");
        return "empleado-form/user-view";
    }

    @PostMapping("/empleadoForm")
    public String createEmpleado(@ModelAttribute("empleadoForm")Empleado empleado, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("empleadoForm", empleado);
            model.addAttribute("formTab", "active");
        }else{
            try {
                empleadoService.createEmpleado(empleado);
                model.addAttribute("empleadoForm", new Empleado());
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("empleadoForm", empleado);
                model.addAttribute("formTab", "active");
            } 
        }
        model.addAttribute("categoriaList", empleadoService.getEmpleados());
        return "empleado-form/user-view";
    }
    
    @GetMapping("/editEmpleado/{id}")
    public String getEditEmpleadoForm(Model model, @PathVariable(name = "id") int id)throws Exception{
        Empleado empleadoToEdit = empleadoService.getById(id);
        model.addAttribute("empleadoForm", empleadoToEdit);
        model.addAttribute("empleadoList", empleadoService.getEmpleados());
        model.addAttribute("formTab", "active");
        model.addAttribute("editMode", "true");

        return "empleado-form/user-view";
    }

    @PostMapping("/editEmpleado")
    public String postEditEmpleadoForm(@ModelAttribute("empleadoForm")Empleado empleado, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("empleadoForm", empleado);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
        }else{
            try {
                empleadoService.updateEmpleado(empleado);
                model.addAttribute("empleadoForm", empleado);
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("empleadoForm", empleado);
                model.addAttribute("formTab", "active");
                model.addAttribute("editMode", "true");
            } 
        }
        model.addAttribute("empleadoList", empleadoService.getEmpleados());
        return "empleado-form/user-view";
    }

    @GetMapping("/editEmpleado/cancel")
    public String cancelEditEmpleado(ModelMap model){
        return "redirect:/empleadoForm";
    }

    @GetMapping("/deleteEmpleado/{id}")
    public String getDeleteEmpleadoForm(Model model, @PathVariable(name = "id") int id)throws Exception{
        Empleado empleadoToDelete = empleadoService.getById(id);
        model.addAttribute("empleadoForm", empleadoToDelete);
        model.addAttribute("empleadoList", empleadoService.getEmpleados());
        model.addAttribute("formTab", "active");
        model.addAttribute("editMode", "true");

        return "empleado-form/user-view";
    }

    @PostMapping("/deleteEmpleado")
    public String postDeleteEmpleadoForm(@ModelAttribute("empleadoForm")Empleado empleado, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("empleadoForm", empleado);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
        }else{
            try {
                empleadoService.deleteEmpleadoLogico(empleado);
                model.addAttribute("empleadoForm", empleado);
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("empleadoForm", empleado);
                model.addAttribute("formTab", "active");
                model.addAttribute("editMode", "true");
            } 
        }
        model.addAttribute("empleadoList", empleadoService.getEmpleados());
        return "cliente-form/user-view";
    }
}
