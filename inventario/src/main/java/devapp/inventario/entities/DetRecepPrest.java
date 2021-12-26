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
    
    private int cantidad;
    private double totalLinea;

    public DetRecepPrest() 
    {
    }

    

    public DetRecepPrest(RecepPrest recepPrest, Producto producto, int cantidad, double totalLinea) {
        this.recepPrest = recepPrest;
        this.producto = producto;
        this.cantidad = cantidad;
        this.totalLinea = totalLinea;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }

    
    


}
