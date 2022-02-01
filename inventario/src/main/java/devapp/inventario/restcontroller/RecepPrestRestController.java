package devapp.inventario.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devapp.inventario.dto.PrestacionEmplDto;
import devapp.inventario.dto.RecepcionDto;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.services.RecepPrestService;
import devapp.inventario.services.RecordNotFoundException;

@RestController
@RequestMapping("/api/v1/recepprest")

public class RecepPrestRestController {
    @Autowired
    RecepPrestService recepPrestService;
    
    // @GetMapping()
    // public ArrayList<RecepPrest> obtenerRecepPrest(){
    //     return recepPrestService.obtenerRecepPrest();
    // }
        //******************Para el cliente */
    



	///*************Para que los empleados puedan realizar reservaciones */
	@PostMapping()
    public ResponseEntity<RecepPrest> savePrestacion(
    @RequestBody PrestacionEmplDto save) throws RecordNotFoundException
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
        userDetails = (UserDetails) principal;
        }
        String userName = userDetails.getUsername();
        System.out.print(userName);
        
        Integer idEmpleado = save.getIdEmpleado(); 
        RecepPrest recepPrest = recepPrestService.savePrestacion(save,idEmpleado);
        
        if(recepPrest!=null)
            return new ResponseEntity<RecepPrest>(recepPrest, new HttpHeaders(), HttpStatus.OK);
        else
        return new ResponseEntity<RecepPrest>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);

        
    }
	//Para modificar el estado de la reservacion
	@PutMapping(path ="/{idR}")
    public RecepPrest updateReservacion( 
        @PathVariable("idR") long idR,
	    @RequestParam(required = false,defaultValue = "update") String action,
	    @RequestBody(required = false) PrestacionEmplDto prestacionDto)
    {
        Integer idEmpleado = prestacionDto.getIdEmpleado();
		if(action.equals("cancel"))
			return recepPrestService.cancelReservacionEmpl(idR, idEmpleado);

        
		if(action.equals("update"))
		{
			if(prestacionDto!=null)
			return recepPrestService.actualizarReservacionEmpl(prestacionDto, idR,idEmpleado);
		}
		return null;
    }


    @PutMapping(path = "/prestacion/{idP}")
    public RecepPrest recepcionar(
        @PathVariable("idP") long idP,
	    @RequestParam(required = false,defaultValue = "update") String action,
	    @RequestBody(required = false) RecepcionDto recepcionDto)
    {
        Integer idEmpleado = recepcionDto.getIdEmpleado();
        if(action.equals("update"))
        {
            
            return recepPrestService.recepcionar(recepcionDto, idP, idEmpleado);
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
