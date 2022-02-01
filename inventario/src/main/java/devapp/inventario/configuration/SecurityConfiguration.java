package devapp.inventario.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/h2-console/**").permitAll().and() 
        .antMatcher("/h2-console/").httpBasic().and().authorizeRequests().anyRequest().permitAll().and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/index")
            .usernameParameter("email")
            .passwordParameter("password")
            .and()
        .logout()
            .permitAll()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/index");

            http.headers().frameOptions().disable();



      
    }
}
