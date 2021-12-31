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
    private String direccion;
    private Date fechaEntrega;
    private double totalPrestacion;
    private double totalPerdida;
    private double valorPagado;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}, mappedBy = "recepPrest",fetch = FetchType.EAGER)
    List<DetRecepPrest> detalles;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE},mappedBy = "recepPrest")
    List<EstRecepPrest> estados;

    private String direccionEntrega;
    private String garantia;
    public RecepPrest()
    {
        this.totalPerdida=0;
    }

    public RecepPrest(Cliente cliente, String direccion, Date fechaEntrega, double totalPrestacion,
            double valorPagado, List<DetRecepPrest> detalles, List<EstRecepPrest> estados) {
        this.cliente = cliente;
        this.direccion = direccion;
        this.fechaEntrega = fechaEntrega;
        this.totalPrestacion = totalPrestacion;
        this.totalPerdida = 0;
        this.valorPagado = valorPagado;
        this.detalles = detalles;
        this.estados = estados;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    //**************************Metodos extras para la prestacion y recepcion */
    public void prestacion(EstRecepPrest estado)
    {
        if(estados==null)
        {
            estados = new ArrayList<EstRecepPrest>();
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

    

    


    
}
