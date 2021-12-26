package devapp.inventario.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROVEEDOR")
public class Proveedor 
{
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idProveedor")
    int id;
    String nombre;
    String direccion;
    String telefono;
    String correo;
    int estado;

    public Proveedor()
    {

    }
    public Proveedor(String nombre, String direccion, String telefono, String correo) 
    {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = 1;
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
    
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    @Override
    public String toString() {
        return "Proveedor [correo=" + correo + ", direccion=" + direccion + ", id=" + id + ", nombre=" + nombre
                + ", telefono=" + telefono + "]";
    }


}
