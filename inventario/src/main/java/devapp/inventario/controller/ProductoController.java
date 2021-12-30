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

import devapp.inventario.entities.Producto;
import devapp.inventario.services.ProductoService;

@RestController
@RequestMapping("/PRODUCTO")
public class ProductoController {
    @Autowired
    ProductoService productoService;
    
    @GetMapping()
    public ArrayList<Producto> obtenerProductos(){
        return productoService.obtenerProductos();
    }

    @PostMapping()
    public Producto guardarProducto(@RequestBody Producto producto){
        return this.productoService.guardarProducto(producto);
    }

    @GetMapping( path = "/{id}")
    public Optional<Producto> obtenerProductos(@PathVariable("id") int id){
        return this.productoService.obtetenerpoId(id);
    }
    
    @DeleteMapping( path = "/{id}")
    public String eliminarProductoId(@PathVariable("id") int id){
        boolean ok = this.productoService.eliminarProducto(id);
        if(ok){
            return "Se elimino el usuario con id "+ id;
        }else{
            return "No se pudo eliminar el usuario con id " + id;
        }
    }
}
