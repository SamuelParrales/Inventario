package devapp.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import devapp.inventario.services.RecepPrestService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    RecepPrestService recepPrestService;

    @GetMapping() 
    public String menu()
    {
        return "empleado/menu_empleado";
    }
    
    @GetMapping("/prestacion")
    public String prestacion()
    {

        return "empleado/prestacion";
    }
    
    @GetMapping("/recepcion/{id}")
    public String recepcion(@PathVariable("id") Long id, Model model)
    {
        model.addAttribute("prestacion", recepPrestService.getPrestacionById(id));
        return "empleado/recepcion";
    }
}
