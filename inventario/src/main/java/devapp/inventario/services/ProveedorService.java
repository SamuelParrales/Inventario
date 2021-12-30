package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Proveedor;
import devapp.inventario.repositories.ProveedorRepository;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<Proveedor>obtenerProveedor(){
        return (ArrayList<Proveedor>)proveedorRepository.findAll();
    }
    
    public Proveedor guardarProveedor(Proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }

    public Optional<Proveedor> obtenerProveedorId(Integer id){
        return proveedorRepository.findById(id); 
    }

    public boolean eliminarProveedor(Integer id){
        try {
            proveedorRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
