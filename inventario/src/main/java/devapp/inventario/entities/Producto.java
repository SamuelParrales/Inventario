package devapp.inventario.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTO")
public class Producto 
{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="idProducto")
    private int id;
    private String nombre;
    private String descripcion;
    private double valorUnitario;
    private int cant;
    

    @JoinColumn(name="idCategoria")
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Categoria categoria;

    
	@JoinTable(
	  name = "producto_prov", 
	  joinColumns = @JoinColumn(name = "idProducto"), 
	  inverseJoinColumns = @JoinColumn(name = "idProveedor"))
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Proveedor> proveedores;

    private int estado;

    //**************Metodos********************* */
    public Producto() {
    }

    public Producto(String nombre, String descripcion, double valorUnitario, int cant, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorUnitario = valorUnitario;
        this.cant = cant;
        this.categoria = categoria;
        this.estado=1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    

    
}


