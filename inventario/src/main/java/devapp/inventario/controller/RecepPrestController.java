package devapp.inventario.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.dto.PrestacionEmplDto;
import devapp.inventario.dto.ReservacionClientDto;
import devapp.inventario.entities.RecepPrest;
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

    @PostMapping("/reservacion")
    public RecepPrest saveReservacion(@RequestBody ReservacionClientDto save)
    {
        return recepPrestService.saveReservacionCliente(save);
    }
    @PostMapping("/prestacion")
    public RecepPrest savePrestacion(@RequestBody PrestacionEmplDto save)
    {
        return recepPrestService.savePrestacionEmpleado(save);
    }

    @PutMapping("/cancelar")
    public RecepPrest cancelar(@RequestParam long idRecepPrest, 
    @RequestParam int idEmpleado)
    {

        return recepPrestService.cancelReservacion(idRecepPrest, idEmpleado);
    }
    
    @GetMapping( path = "/{id}")
    public RecepPrest obtenerRecepPrest(@PathVariable("id") long id)
    {
        return this.recepPrestService.getById(id);
    }    
}
