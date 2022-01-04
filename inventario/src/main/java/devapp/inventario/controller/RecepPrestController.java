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
        //******************Para el cliente */
    @PostMapping(path = "/cliente/{idC}/reservacion/")
    public RecepPrest saveReservacion(@PathVariable("idC") int idCliente,
        @RequestBody ReservacionClientDto save)
    {
        return recepPrestService.saveReservacionCliente(save,idCliente);
    }
    
    @PutMapping(path =  "/cliente/{idC}/reservacion/{idR}")
    public RecepPrest updateClientReservacion(@PathVariable("idC") int idCliente,
        @PathVariable("idR") long idReser,
        @RequestBody ReservacionClientDto reservacionDto)
    {
        //id: Id de la reservacion
        return recepPrestService.actualizarReservacionClient(reservacionDto, idReser,idCliente);
    }

    @PutMapping("/cancelar")
    public RecepPrest cancelar(@RequestParam long idRecepPrest, 
    @RequestParam int idEmpleado)
    {

        return recepPrestService.cancelReservacionEmpl(idRecepPrest, idEmpleado);
    }

    //Para los empleados
    @PostMapping(path = "/empleado/{idE}/prestacion/")
    public RecepPrest savePrestacion(@PathVariable("idE") int idEmpleado,
        @RequestBody PrestacionEmplDto save)
    {
    
        return recepPrestService.savePrestacion(save,idEmpleado);
    }

    @PutMapping(path ="/empleado/{idE}/reservacion/{idR}")
    public RecepPrest updateReservacion( @PathVariable("idE") int idEmpleado,
        @PathVariable("idR") long idR,@RequestBody PrestacionEmplDto prestacionDto)
    {
      
        return recepPrestService.actualizarReservacionEmpl(prestacionDto, idR,idEmpleado);
    }

    //Aun no definido bien
    @GetMapping( path = "/{id}")
    public RecepPrest obtenerRecepPrest(@PathVariable("id") long id)
    {
        return this.recepPrestService.getById(id);
    }    
}
