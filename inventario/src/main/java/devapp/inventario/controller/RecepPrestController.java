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
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.services.RecepPrestService;

@RestController
@RequestMapping("/recepprest")

public class RecepPrestController {
    @Autowired
    RecepPrestService recepPrestService;
    
    // @GetMapping()
    // public ArrayList<RecepPrest> obtenerRecepPrest(){
    //     return recepPrestService.obtenerRecepPrest();
    // }
        //******************Para el cliente */
    



	///*************Para que los empleados puedan realizar reservaciones */
	@PostMapping()
    public RecepPrest savePrestacion(
    @RequestParam int idEmpleado,
    @RequestBody PrestacionEmplDto save)
    {
    
        return recepPrestService.savePrestacion(save,idEmpleado);
    }
	//Para modificar el estado de la reservacion
	@PutMapping(path ="/{idR}")
    public RecepPrest updateReservacion( 
    @PathVariable("idR") long idR,
    @RequestParam() Integer idEmpleado,
	@RequestParam(required = false,defaultValue = "update") String action,
	@RequestBody(required = false) PrestacionEmplDto prestacionDto)
    {
		if(action.equals("cancel"))
			return recepPrestService.cancelReservacionEmpl(idR, idEmpleado);

		if(action.equals("update"))
		{
			if(prestacionDto!=null)
			return recepPrestService.actualizarReservacionEmpl(prestacionDto, idR,idEmpleado);
		}
		return null;
    }



    //Aun no definido bien
    @GetMapping( path = "/{id}")
    public RecepPrest obtenerRecepPrest(@PathVariable("id") long id)
    {
        return this.recepPrestService.getById(id);
    }    
}
