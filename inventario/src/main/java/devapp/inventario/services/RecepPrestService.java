package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.RecepPrestRepository;

@Service
public class RecepPrestService {

    @Autowired
    RecepPrestRepository recepPrestRepository;

    public ArrayList<RecepPrest> obtenerRecepPrest(){
        return (ArrayList<RecepPrest>)recepPrestRepository.findAll();
    }


    public RecepPrest guardarRecepPrest(RecepPrest recepPrest){
        return recepPrestRepository.save(recepPrest);
    }

    public Optional<RecepPrest> obtetenerRecepId(long id){
        return recepPrestRepository.findById(id); 
    }

    public boolean eliminarRecepPrest(long id){
        try {
            recepPrestRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    
}
