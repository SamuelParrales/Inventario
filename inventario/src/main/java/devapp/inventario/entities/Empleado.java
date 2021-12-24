package devapp.inventario.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empleado")
public class Empleado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idempleado;
	
	private String ci;
	private String tipo;
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	
	public Empleado()
	{
		
	}
	public Empleado(String ci, String tipo, String nombre, String apellido, String correo, String password) {
		super();
		this.ci = ci;
		this.tipo = tipo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.password = password;
	}

	public String getCi() {
		return ci;
	}
	
	public void setCi(String ci) {
		this.ci = ci;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return idempleado;
	}

	@Override
	public String toString() {
		return "Empleado [idempleado=" + idempleado + ", ci=" + ci + ", tipo=" + tipo + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", correo=" + correo + ", password=" + password + "]";
	}
	
}
