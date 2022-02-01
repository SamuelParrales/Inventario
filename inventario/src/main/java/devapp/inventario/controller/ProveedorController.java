package devapp.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
