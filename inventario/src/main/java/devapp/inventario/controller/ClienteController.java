package devapp.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController 
{
    @GetMapping("/carrito") 
    public String cart()
    {
        return "cliente/carrito";
    }
}
