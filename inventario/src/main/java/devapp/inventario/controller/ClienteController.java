package devapp.inventario.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.RecepPrestRepository;

@Controller
@RequestMapping("/cliente")
public class ClienteController 
{

    @Autowired
    private BCryptPasswordEncoder BCrypt;
    
    @Autowired
    ClienteRepository clienterepository;

    @Autowired
    RecepPrestRepository reservacionRepo;

    @GetMapping()
    public String perfil(Model model)
    {
        Cliente cliente;
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
			}
			String email = userDetails.getUsername();
			cliente = clienterepository.findByCorreo(email);
            
		}
		catch(Exception e)
		{
			return null;
		}
        model.addAttribute("usuario", cliente);
        return "perfil";
    }

    @GetMapping("/carrito") 
    public String cart()
    {
        return "cliente/carrito";
    }

    @RequestMapping("/save")
    public String save(Cliente cliente){
        String pass = cliente.getPassword(); //estrae la contraseña sola
        cliente.setEstado(1);
        cliente.setPassword(BCrypt.encode(pass)); //codifica la contraseña
        cliente.setCelular("sin definir xd");
        clienterepository.save(cliente);
        return "redirect:/login";
    }
    @GetMapping("/reservacion")
    public String reservacion(Model model,@RequestParam(required = false,defaultValue = "1") Integer pag)
    {
        Cliente cliente;
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
			}
			String email = userDetails.getUsername();
			cliente = clienterepository.findByCorreo(email);
			
		}
		catch(Exception e)
		{
			return null;
		}
        Pageable p = PageRequest.of(pag-1, 12);
        model.addAttribute("reservacion", reservacionRepo.findAllByClienteOrderByIdDesc(cliente, p));        
        
        return "cliente/reservacion";
    }

    @GetMapping("/reservacion/{id}")
    public String detReservacion(Model model,@PathVariable("id") Long id)
    {
        Cliente cliente;
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
			}
			String email = userDetails.getUsername();
			cliente = clienterepository.findByCorreo(email);
            
		}
		catch(Exception e)
		{
			return null;
		}
        RecepPrest r= reservacionRepo.findByIdAndCliente(id, cliente);
        if(r!=null)
        {
            
            model.addAttribute("reservacion",r);  
            return "factura_recep_prest";      
        }


        return "redirect:/cliente/reservacion";
    }
}
