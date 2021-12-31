package devapp.inventario.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import devapp.inventario.entities.request.GeneratePrestacion;
import devapp.inventario.services.RecepPrestService;

@RestController
@RequestMapping("/RECEPPREST")

public class RecepPrestController {
    @Autowired
    RecepPrestService recepPrestService;
    
    // @GetMapping()
    // public ArrayList<RecepPrest> obtenerRecepPrest(){
    //     return recepPrestService.obtenerRecepPrest();
    // }

    @PostMapping()
    public RecepPrest savePrestacion(@RequestBody GeneratePrestacion save)
    {
        List<List<Integer>> productos = save.getProductos(); //[[id,cant],[id,cant]]
        double pago = save.getPago();
        int idEmpleado = save.getIdEmpleado();
        int idCliente = save.getIdCliente();
        Date fechaEntrega = new Date(save.getFechaEntrega());
        String direccionEntrega = save.getDireccionEntrega();
        String garantia = save.getGarantia();

        return recepPrestService.savePrestacion(productos, pago, idEmpleado, idCliente,fechaEntrega,
        direccionEntrega,garantia);
        
    }

    @GetMapping( path = "/{id}")
    public RecepPrest obtenerRecepPrest(@PathVariable("id") long id){
        return this.recepPrestService.getById(id);
    }
    
    
    
}
