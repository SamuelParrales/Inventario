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

	public boolean ValidarCI(String CI) {
		this.ci=CI;
		boolean bandera=false;
		Pattern pat=Pattern.compile("[0-9]");
		Matcher matcher = pat.matcher(ci);
		
		
		if (matcher.find()) {
			if (ci.length()==10) {
				if (this.DigitoVerificador(ci)) bandera=true; //digito verificador
			} else bandera=false;
		}
		return bandera;
		
	}
	
	private boolean DigitoVerificador(String document) {
	    byte sum = 0;
	    try {
	        String[] data = document.split("");
	        byte verifier = Byte.parseByte(data[0] + data[1]);
	        if (verifier < 1 || verifier > 24)
	            return false;
	        byte[] digits = new byte[data.length];
	        for (byte i = 0; i < digits.length; i++)
	            digits[i] = Byte.parseByte(data[i]);
	        if (digits[2] > 6)
	            return false;
	        for (byte i = 0; i < digits.length - 1; i++) {
	            if (i % 2 == 0) {
	                verifier = (byte) (digits[i] * 2);
	                if (verifier > 9)
	                    verifier = (byte) (verifier - 9);
	            } else
	                verifier = (byte) (digits[i] * 1);
	            sum = (byte) (sum + verifier);
	        }
	        if ((sum - (sum % 10) + 10 - sum) == digits[9])
	            return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return false;
	}
	
	
}
