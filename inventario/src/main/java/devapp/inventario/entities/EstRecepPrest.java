package devapp.inventario.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import devapp.inventario.entities.compositepk.EstRecepPrestPk;

@Entity
@Table(name = "ESTADO_RECEP_PREST")
@IdClass(EstRecepPrestPk.class)
public class EstRecepPrest 
{
    
    @Id
    @JoinColumn(name = "idRecepPrest",nullable = false)
    @ManyToOne
    private RecepPrest recepPrest;

    @Id
    private boolean estado;

    @JoinColumn(name="idEmpleado",nullable = false)
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Empleado empleado;

    private Date fecha;

    public EstRecepPrest() {
    }

    public RecepPrest getRecepPrest() {
        return recepPrest;
    }
    public EstRecepPrest(RecepPrest recepPrest, boolean estado, Empleado empleado, Date fecha) {
        this.recepPrest = recepPrest;
        this.estado = estado;
        this.empleado = empleado;
        this.fecha = fecha;
    }

    public void setRecepPrest(RecepPrest recepPrest) {
        this.recepPrest = recepPrest;
    }
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

   
    
    

    
}
