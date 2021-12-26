package devapp.inventario.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidacionUsuarios {
	private String correo;
	private String ci;
	private String telefono;
	private String password;
	
	//definicion de getters y setters
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean ValidarCorreo(String email) {
		this.correo=email;
		boolean bandera = false;
		Pattern pat = Pattern.compile("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
		Matcher matcher = pat.matcher(email);
		if (matcher.find()) {
			bandera=true;
		}
		return bandera;
		
	}
	
}
