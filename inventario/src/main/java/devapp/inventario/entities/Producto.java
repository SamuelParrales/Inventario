package devapp.inventario.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="producto")
public class Producto {
	
	public Producto()
	{
		
	}
	
	
	public Producto(String nombre, String descripcion, float valorunitario, int cantidad, Set<Proveedores> proveedores,
			Categoria categoria) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.valorunitario = valorunitario;
		this.cantidad = cantidad;
		this.proveedores = proveedores;
		this.categoria = categoria;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproducto;
	private String nombre;
	private String descripcion;
	private float valorunitario;
	private int cantidad;
	
	@ManyToMany(mappedBy = "productos")
	private Set<Proveedores> proveedores;
	
	@JoinColumn(name="idcategoria")
	@ManyToOne(cascade = CascadeType.MERGE)		//Hija de cliente
	private Categoria categoria;
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValorunitario() {
		return valorunitario;
	}

	public void setValorunitario(float valorunitario) {
		this.valorunitario = valorunitario;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdproducto() {
		return idproducto;
	}



	public Set<Proveedores> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Set<Proveedores> proveedores) {
		this.proveedores = proveedores;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void addProveedor(Proveedores proveedor) {
        this.proveedores.add(proveedor);
        proveedor.getProductos().add(this);
    }
    public void removeProducto(Proveedores proveedor) {
        this.proveedores.remove(proveedor);
        proveedor.getProductos().remove(this);
    }


	@Override
	public String toString() {
		return "Producto [idproducto=" + idproducto + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", valorunitario=" + valorunitario + ", cantidad=" + cantidad + ", proveedores=" 
				+ ", categoria=" + categoria.getId() + "]";
	}


	
	
	
}
