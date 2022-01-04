package devapp.inventario.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import devapp.inventario.services.AutenticacionService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    AutenticacionService userDetailsService;

    @Autowired
    BCryptPasswordEncoder BCrypt;


    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
		return Encoder;
	}

    @Autowired
	public void configure(AuthenticationManagerBuilder auth) 
		throws Exception{

	    auth.userDetailsService(this.userDetailsService).passwordEncoder(BCrypt);
			
		}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
        .antMatchers("/h2-console/").authenticated().and().httpBasic().and()
        .antMatcher("/h2-console/").authorizeRequests().anyRequest().permitAll();


    }



}
