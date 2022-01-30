package devapp.inventario.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Empleado;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.EmpleadoRepository;
@Service
public class AutenticacionService implements UserDetailsService{

    @Autowired
    EmpleadoRepository empleadoreposository;

    @Autowired
    ClienteRepository clienteRepo;


    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Empleado empleadoTemp = empleadoreposository.findByCorreo(correo);
        List<GrantedAuthority> roles = new ArrayList<>();

        if(empleadoTemp==null)  //Sino es empleado es cliente
        {
            Cliente cliente = clienteRepo.findByCorreo(correo);
            roles.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
            UserDetails userDetails = new User(cliente.getNombres()+" "+cliente.getApellidos(),cliente.getPassword(),roles);
            return userDetails;
        }

        if(empleadoTemp.getTipo().equals("admin"));
        {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
        }
        roles.add(new SimpleGrantedAuthority("ROLE_DESPACHADOR"));


        UserDetails userDetails = new User(empleadoTemp.getNombres() +" "+empleadoTemp.getApellidos(),empleadoTemp.getPassword(),roles);
        return userDetails;
    }
    
}
