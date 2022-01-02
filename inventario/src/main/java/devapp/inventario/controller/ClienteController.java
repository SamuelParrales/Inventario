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

import devapp.inventario.entities.Cliente;
import devapp.inventario.services.ClienteService;

@Controller
public class ClienteController {
    @Autowired
    ClienteService clienteService;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/clienteForm")
    public String clienteForm(Model model){
        model.addAttribute("clienteForm", new Cliente());
        model.addAttribute("clienteList", clienteService.getClientes());
        model.addAttribute("listTab", "active");
        return "cliente-form/user-view";
    }

    @PostMapping("/clienteForm")
    public String createCliente(@ModelAttribute("clienteForm")Cliente cliente, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("clienteForm", cliente);
            model.addAttribute("formTab", "active");
        }else{
            try {
                clienteService.createCliente(cliente);
                model.addAttribute("clienteForm", new Cliente());
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("clienteForm", cliente);
                model.addAttribute("formTab", "active");
            } 
        }
        model.addAttribute("categoriaList", clienteService.getClientes());
        return "cliente-form/user-view";
    }
    
    @GetMapping("/editCliente/{id}")
    public String getEditClienteForm(Model model, @PathVariable(name = "id") int id)throws Exception{
        Cliente clienteToEdit = clienteService.getById(id);
        model.addAttribute("clienteForm", clienteToEdit);
        model.addAttribute("clienteList", clienteService.getClientes());
        model.addAttribute("formTab", "active");
        model.addAttribute("editMode", "true");

        return "cliente-form/user-view";
    }

    @PostMapping("/editCliente")
    public String postEditClienteForm(@ModelAttribute("clienteForm")Cliente cliente, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("clienteForm", cliente);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
        }else{
            try {
                clienteService.updateCliente(cliente);
                model.addAttribute("clienteForm", cliente);
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("clienteForm", cliente);
                model.addAttribute("formTab", "active");
                model.addAttribute("editMode", "true");
            } 
        }
        model.addAttribute("clienteList", clienteService.getClientes());
        return "cliente-form/user-view";
    }

    @GetMapping("/editCliente/cancel")
    public String cancelEditCliente(ModelMap model){
        return "redirect:/clienteForm";
    }

    @GetMapping("/deleteCliente/{id}")
    public String getDeleteClienteForm(Model model, @PathVariable(name = "id") int id)throws Exception{
        Cliente clienteToDelete = clienteService.getById(id);
        model.addAttribute("clienteForm", clienteToDelete);
        model.addAttribute("clienteList", clienteService.getClientes());
        model.addAttribute("formTab", "active");
        model.addAttribute("editMode", "true");

        return "cliente-form/user-view";
    }

    @PostMapping("/deleteCliente")
    public String postDeleteClienteForm(@ModelAttribute("clienteForm")Cliente cliente, BindingResult result,ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("clienteForm", cliente);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
        }else{
            try {
                clienteService.deleteClienteLogico(cliente);
                model.addAttribute("clienteForm", cliente);
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("clienteForm", cliente);
                model.addAttribute("formTab", "active");
                model.addAttribute("editMode", "true");
            } 
        }
        model.addAttribute("clienteList", clienteService.getClientes());
        return "cliente-form/user-view";
    }

    @GetMapping("/deleteCliente/cancel")
    public String cancelDeleteCliente(ModelMap model){
        return "redirect:/clienteForm";
    }
}
