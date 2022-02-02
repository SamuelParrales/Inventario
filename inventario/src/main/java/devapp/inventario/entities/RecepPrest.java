package devapp.inventario.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RECEPCIÓN_PRESTACIÓN")
public class RecepPrest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="idRecpPrest")
    private long id;

    @JoinColumn(name = "idCliente")
    @ManyToOne
    private Cliente cliente;
    private Date fechaCaducida;
    private double totalPrestacion;
    private double totalPerdida;
    private double valorPagado;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "recepPrest",orphanRemoval = true, fetch = FetchType.EAGER)
    List<DetRecepPrest> detalles;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE},mappedBy = "recepPrest")
    List<EstRecepPrest> estados;

    private String direccionEntrega;
    private String garantia;
    public RecepPrest()
    {
        this.totalPerdida=0;
    }

  

    public RecepPrest(long id, Cliente cliente, Date fechaCaducida, double totalPrestacion,
            double valorPagado, List<DetRecepPrest> detalles, List<EstRecepPrest> estados,
            String direccionEntrega) {
        this.id = id;
        this.cliente = cliente;
        this.fechaCaducida = fechaCaducida;
        this.totalPrestacion = totalPrestacion;
        this.totalPerdida = 0;
        this.valorPagado = valorPagado;
        this.detalles = detalles;
        this.estados = estados;
        this.direccionEntrega = direccionEntrega;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public Date getFechaCaducida() {
        return fechaCaducida;
    }

    public void setFechaCaducida(Date fechaCaducida) {
        this.fechaCaducida = fechaCaducida;
    }



    public double getTotalPrestacion() {
        return totalPrestacion;
    }

    public void setTotalPrestacion(double totalPrestacion) {
        this.totalPrestacion = totalPrestacion;
    }

    public double getTotalPerdida() {
        return totalPerdida;
    }

    public void setTotalPerdida(double totalPerdida) {
        this.totalPerdida = totalPerdida;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public List<DetRecepPrest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetRecepPrest> detalles) {
        this.detalles = detalles;
    }

    public List<EstRecepPrest> getEstados() {
        return estados;
    }

    public void setEstados(List<EstRecepPrest> estados) {
        this.estados = estados;
    }


    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    

        //**************************Metodos extras para la prestacion y recepcion */
        public void prestacion(EstRecepPrest estado)
        {
            if(estados==null)
            {
                estados = new ArrayList<EstRecepPrest>();
                if(estado.getEstado()!=5)
                {
                    EstRecepPrest primerEstado = new EstRecepPrest(this, 5, estado.getEmpleado());
                    estados.add(primerEstado);
                }


                estados.add(estado);
            }
        }
    
        public void cambiarEstado(EstRecepPrest estado)
        {
            if(estados!=null)
            estados.add(estado);
        }
    
        public int estadoActual()
        {
            int len = estados.size();
            return estados.get(len-1).getEstado();
        }

        public boolean poseeElEstado(int est)
        {
            for(EstRecepPrest estado: estados)
            {
                if(estado.getEstado()==est)
                    return true;
            }
            return false;
        }
}
