package devapp.inventario.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name="proveedores")
public class Proveedores {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproveedor;
	
	
	private String nombre;
	private String direccion;
	private String telefono;
	private String correo;
	
	@ManyToMany
	@JoinTable(
	  name = "producto_prov", 
	  joinColumns = @JoinColumn(name = "id_proveedores"), 
	  inverseJoinColumns = @JoinColumn(name = "id_producto"))
	private Set<Producto> productos;
	
	
	public Proveedores()
	{
		
	}
	
	
	public Proveedores(String nombre, String direccion, String telefono, String correo) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
		this.productos = productos;
	}


	public int getIdproveedor() {
		return idproveedor;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}




	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public void addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getProveedores().add(this);
    }
    public void removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getProveedores().remove(this);
    }
	
}
