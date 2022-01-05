package devapp.inventario.dto;

import java.util.List;

public class ProductoDto {
    private int id;
    private String nombre;
    private String descripcion;
    private double valorUnitario;
    private int cantDisponible;
    private double valorPrestacion;
    private int cantPrestada;
    private int idCategoria;
    private List<Integer> idProveedores;
    private int estado;

    public ProductoDto() {
    }

    public ProductoDto(int id, String nombre, String descripcion, double valorUnitario, int cantDisponible,
            double valorPrestacion, int cantPrestada, int idCategoria, List<Integer> idProveedores, 
            int estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorUnitario = valorUnitario;
        this.cantDisponible = cantDisponible;
        this.valorPrestacion = valorPrestacion;
        this.cantPrestada = cantPrestada;
        this.idCategoria = idCategoria;
        this.idProveedores = idProveedores;
        this.estado = estado;
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

    public int getCantDisponible() {
        return cantDisponible;
    }

    public void setCantDisponible(int cantDisponible) {
        this.cantDisponible = cantDisponible;
    }

    public double getValorPrestacion() {
        return valorPrestacion;
    }

    public void setValorPrestacion(double valorPrestacion) {
        this.valorPrestacion = valorPrestacion;
    }

    public int getCantPrestada() {
        return cantPrestada;
    }

    public void setCantPrestada(int cantPrestada) {
        this.cantPrestada = cantPrestada;
    }

    public List<Integer> getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(List<Integer> idProveedores) {
        this.idProveedores = idProveedores;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
