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

import devapp.inventario.entities.RecepPrest;
import devapp.inventario.services.RecepPrestService;

@RestController
@RequestMapping("/RECEPPREST")

public class RecepPrestControllar {
    @Autowired
    RecepPrestService recepPrestService;
    
    @GetMapping()
    public ArrayList<RecepPrest> obtenerRecepPrest(){
        return recepPrestService.obtenerRecepPrest();
    }

    @PostMapping()
    public RecepPrest guardarRecepPrest(@RequestBody RecepPrest recepPrest){
        return this.recepPrestService.guardarRecepPrest(recepPrest);
    }

    @GetMapping( path = "/{id}")
    public Optional<RecepPrest> obtenerRecepPrest(@PathVariable("id") long id){
        return this.recepPrestService.obtetenerRecepId(id);
    }
    
    @DeleteMapping( path = "/{id}")
    public String eliminarRecepPrest(@PathVariable("id") long id){
        boolean ok = this.recepPrestService.eliminarRecepPrest(id);
        if(ok){
            return "Se elimino el usuario con id "+ id;
        }else{
            return "No se pudo eliminar el usuario con id " + id;
        }
    }
    
}
