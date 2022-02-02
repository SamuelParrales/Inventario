package devapp.inventario.controller;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.joran.conditional.ElseAction;
import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.RecepPrestRepository;
import devapp.inventario.services.RecepPrestService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    RecepPrestService recepPrestService;

    @Autowired
    RecepPrestRepository recepPrestRepo;

    @Autowired
    ClienteRepository clienteRepo;

    @GetMapping("/menu") 
    public String menu()
    {
        return "empleado/menu_empleado";
    }
    
    @GetMapping("/prestacion")
    public String prestacion()
    {
        return "empleado/prestacion";
    }

    @GetMapping("/reservacion/{id}")
    public String reservacion(@PathVariable("id") Long id, Model model)
    {
        RecepPrest reservacion = null;
        try
        {
            reservacion = recepPrestRepo.findById(id).get();
        }
        catch(Exception e)
        {

        }
        model.addAttribute("reservacion", reservacion);
        return "empleado/prestacion";
    }

    @GetMapping("/recepcion")
    public String recepcion(
        @RequestParam(required = false,defaultValue = "") String fechai,
        @RequestParam(required = false,defaultValue = "") String fechaf,
        @RequestParam(required = false,defaultValue = "") Long id,
        @RequestParam(required = false,defaultValue = "") String ci,
        Model model)
    {
        int pag =1;
        Pageable p = PageRequest.of(pag-1, 12);
        List<RecepPrest> prestaciones = new ArrayList<RecepPrest>();

        boolean consultado = false;
        try
        {
            
            if(id!=null)
            {
                
                if(ci==null||ci.equals(""))
                {
                    consultado=true;
                    RecepPrest recepPrest = recepPrestRepo.findById(id).get();
                    if(recepPrest.poseeElEstado(3)&&!recepPrest.poseeElEstado(0))
                        prestaciones.add(recepPrest);
                }
                else
                {
                    Cliente cliente = clienteRepo.findByCi(ci);
                    if(cliente!=null)
                    {
                        consultado=true;
                        RecepPrest recepPrest = recepPrestRepo.findByIdAndCliente(id, cliente);
                        if(recepPrest.poseeElEstado(3)&&!recepPrest.poseeElEstado(0))
                            prestaciones.add(recepPrest);
                    }
                }
            }
            else 
            {
                
                if((!ci.equals("")))
                {
                    
                    consultado = true;
                    Cliente cliente = clienteRepo.findByCi(ci);
                    prestaciones= recepPrestRepo.findPrestacionByIdCliente(cliente.getId(), p);
                
                }
    
                if(!fechai.equals("")||!fechaf.equals(""))
                {
                    consultado = true;
                    if(fechai.equals(""))
                        fechai = "2000-01-01";
        
                    if(fechaf.equals(""))
                    {
                        int diaS = LocalDate.now().getDayOfMonth()+1;
                        fechaf = LocalDate.now().withDayOfMonth(diaS).toString();
                    }
                        
                    prestaciones = recepPrestRepo.findPrestacionByFechas(fechai, fechaf,p);
                }
            }

 
        }
        catch(Exception e)
        {

        }

   
        if(!consultado)
            prestaciones = recepPrestRepo.findAllPrestacion(p);

        model.addAttribute("prestaciones", prestaciones);

        return "empleado/search_prestacion";
    }

    @GetMapping("/recepcion/{id}")
    public String recepcionar(@PathVariable("id") Long id, Model model)
    {
         
        model.addAttribute("prestacion", recepPrestService.getPrestacionById(id));
        return "empleado/recepcion";


    }

    @GetMapping("/recep_prest")
    public String reporteRecepPrest(
        @RequestParam(required = false,defaultValue = "") String fechai,
        @RequestParam(required = false,defaultValue = "") String fechaf,
        @RequestParam(required = false,defaultValue = "") Long id,
        @RequestParam(required = false,defaultValue = "") String ci,
        @RequestParam(required = false,defaultValue = "") Integer estado,
        Model model)
    {
        int pag =1;
        Pageable p = PageRequest.of(pag-1, 12);
        List<RecepPrest> recepPrests = new ArrayList<RecepPrest>();
        try
        {
            Boolean consultado = false;
            if(id!=null)
            {
                recepPrests.add(recepPrestRepo.findById(id).get());
                consultado = true;
            }


            if(!consultado &&!ci.equals(""))
            {
                Cliente cliente = clienteRepo.findByCi(ci);
                recepPrests= recepPrestRepo.findAllByClienteOrderByIdDesc(cliente, p);
                consultado = true;
            }

            if(!consultado&&estado!=null)
            {
                recepPrests= recepPrestRepo.findAllByEstados_EstadoOrderByIdDesc(estado, p);
                consultado = true;
            }

            if(!consultado &&(!fechai.equals("")||!fechaf.equals("")))
            {
        
                consultado=true;
                if(fechai.equals(""))
                    fechai = "2000-01-01";
    
                if(fechaf.equals(""))
                {
                    int diaS = LocalDate.now().getDayOfMonth()+1;
                    fechaf = LocalDate.now().withDayOfMonth(diaS).toString();
                }
                
                recepPrests = recepPrestRepo.findByFechas(fechai, fechaf, p);
            }

            if(!consultado)
            {
             
                recepPrests = recepPrestRepo.findAllByEstados_EstadoOrderByIdDesc(5,p);
            }
        }
        catch(Exception e)
        {

        }
 
        model.addAttribute("recePrests", recepPrests);
        return "empleado/search_recepprest";

    }
    @GetMapping("/recep_prest/{id}")
    public String reporteRecepPrest(@PathVariable("id") Long id, Model model)
    {
        
        model.addAttribute("reservacion", recepPrestRepo.findById(id).get());
        return "factura_recep_prest";
    }
}
