package devapp.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import devapp.inventario.dto.PrestacionEmplDto;
import devapp.inventario.services.RecepPrestService;

@Controller
@RequestMapping("/recepprest")
public class RecepPrestController 
{//
    @Autowired
    RecepPrestService recepPrestService;
    @GetMapping()
    public String prestacion()
    {

        return "prestacion";
    }

    @PostMapping()
    public String savePrestacion(@RequestBody PrestacionEmplDto save)
    {
        Integer idEmpleado = save.getIdEmpleado();
        recepPrestService.savePrestacion(save,idEmpleado);
        return "controller";
    }
}
