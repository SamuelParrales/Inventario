package devapp.inventario.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import devapp.inventario.entities.Proveedor;
import devapp.inventario.repositories.ProveedorRepository;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    ProveedorRepository proveedorrepository;
    
    @GetMapping("/register")
    public String register(){
        return "registrarproveedores";
    }   
    

    @RequestMapping("/save")
    public String save(Proveedor proveedor){
        proveedor.setEstado(1);
        proveedorrepository.save(proveedor);
        return "registrarproveedores";
    }

    @RequestMapping("/search")
    public String search(){
        return "search_supplier";
    }

    @RequestMapping("/edit")
    public String edit(Model model, Proveedor proveedor){
    
        Proveedor TempP = proveedorrepository.findByNombre(proveedor.getNombre());
        //if (TempP != null){
            model.addAttribute("id",TempP.getId());
            model.addAttribute("nombre", TempP.getNombre());
            model.addAttribute("direccion",TempP.getDireccion());
            model.addAttribute("telefono",TempP.getTelefono());
            model.addAttribute("correo", TempP.getCorreo());
            
            return "edit_supplier";
        
        //}
        //else return "redirect: /search";
    }

    @RequestMapping("/update")
    public String update(Proveedor proveedor){
        Optional <Proveedor> update = proveedorrepository.findById(proveedor.getId());
        if(update.isPresent()){
            proveedorrepository.save(proveedor);
        }
        return "search_supplier";
    }


}
