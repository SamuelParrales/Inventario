package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Producto;
import devapp.inventario.repositories.ProductoRepository;

@Service

public class ProductoService{
    @Autowired
    ProductoRepository productoRepository;

    public ArrayList<Producto> obtenerProductos(){
        return (ArrayList<Producto>)productoRepository.findAll();
    }


    public Producto guardarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public Optional<Producto> obtetenerpoId(int id){
        return productoRepository.findById(id); 
    }

    public boolean eliminarProducto(int id){
        try {
            productoRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}