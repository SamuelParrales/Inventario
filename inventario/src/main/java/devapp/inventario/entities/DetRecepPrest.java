package devapp.inventario.entities;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import devapp.inventario.entities.compositepk.DetRecepPrestPk;

@Entity
@Table(name = "DETALLE_RECEP_PREST")
@IdClass(DetRecepPrestPk.class)
public class DetRecepPrest 
{
    @Id
    @JoinColumn(name = "idRecpPrest",nullable = false)
    @ManyToOne
    private RecepPrest recepPrest;

    @Id
    @JoinColumn(name="idProducto")
    @ManyToOne
    private Producto producto;
    
    private int cantidadPrestada;
    private double totalPrestacion;
    
    private int cantidadPerdida;
    private double totalPerdida;

    public DetRecepPrest() 
    {
    }
    
    public DetRecepPrest(RecepPrest recepPrest, Producto producto, int cantidadPrestada, double totalPrestacion) {
        this.recepPrest = recepPrest;
        this.producto = producto;
        this.cantidadPrestada = cantidadPrestada;
        this.totalPrestacion = totalPrestacion;
        this.cantidadPerdida = 0;
        this.totalPerdida = 0;
    }

    public RecepPrest getRecepPrest() {
        return recepPrest;
    }

    public void setRecepPrest(RecepPrest recepPrest) {
        this.recepPrest = recepPrest;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public int getCantidadPrestada() {
        return cantidadPrestada;
    }
    public void setCantidadPrestada(int cantidadPrestada) {
        this.cantidadPrestada = cantidadPrestada;
    }
    public double getTotalPrestacion() {
        return totalPrestacion;
    }
    public void setTotalPrestacion(double totalPrestacion) {
        this.totalPrestacion = totalPrestacion;
    }
    public int getCantidadPerdida() {
        return cantidadPerdida;
    }
    public void setCantidadPerdida(int cantidadPerdida) {
        this.cantidadPerdida = cantidadPerdida;
    }
    public double getTotalPerdida() {
        return totalPerdida;
    }
    public void setTotalPerdida(double totalPerdida) {
        this.totalPerdida = totalPerdida;
    }
}
