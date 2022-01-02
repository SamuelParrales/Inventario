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

import devapp.inventario.entities.Empleado;
import devapp.inventario.repositories.EmpleadoRepository;
@Service
public class AutenticacionService implements UserDetailsService{

    @Autowired
    EmpleadoRepository empleadoreposository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleadoTemp = empleadoreposository.findByNombres(username);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("admin"));
        UserDetails userDetails = new User(empleadoTemp.getNombres(),empleadoTemp.getPassword(),roles);
        return userDetails;
    }
    
}
