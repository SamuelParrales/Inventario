package devapp.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import devapp.inventario.entities.Cliente;
import devapp.inventario.repositories.ClienteRepository;

@Controller
@RequestMapping("/cliente")
public class ClienteController 
{

    @Autowired
    private BCryptPasswordEncoder BCrypt;

    @Autowired
    ClienteRepository clienterepository;

    @Autowired

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
}
