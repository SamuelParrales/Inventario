package devapp.inventario.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.entities.Proveedor;
import devapp.inventario.services.ProveedorService;

@RestController
@RequestMapping("/PROVEEDOR")
public class ProveedorController {
    
    @Autowired
    ProveedorService proveedorService;

    @GetMapping()
    public ArrayList<Proveedor> obtenerProveedor(){
        return proveedorService.obtenerProveedor();
    }


    @PostMapping()
    public Proveedor guardarProveedor(@RequestBody Proveedor proveedor){
        return this.proveedorService.guardarProveedor(proveedor);
    }

    @GetMapping( path = "/{id}")
    public Optional<Proveedor> obtenerProveedor(@PathVariable("id") Integer id){
        return this.proveedorService.obtenerProveedorId(id);
    }
    
    @DeleteMapping( path = "/{id}")
    public String eliminarProveedorId(@PathVariable("id") Integer id){
        boolean ok = this.proveedorService.eliminarProveedor(id);
        if(ok){
            return "Se elimino el usuario con id "+ id;
        }else{
            return "No se pudo eliminar el usuario con id " + id;
        }
    }
}
