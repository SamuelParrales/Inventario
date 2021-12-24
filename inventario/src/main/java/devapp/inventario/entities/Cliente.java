package devapp.inventario.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//
@Entity
@Table(name="cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcliente;
	
	private String ci;
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	
	public Cliente(String ci, String nombre, String apellido, String correo, String password) {
		super();
		this.ci = ci;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.password = password;
	}
	public Cliente()
	{
		
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		        return String.format("Cliente[id=%d,ci='%s', nombre='%s', apellido='%s', correo='%s',  passwor='%s']",
		        		 idcliente,ci, nombre, apellido,correo,password);
	}
}

