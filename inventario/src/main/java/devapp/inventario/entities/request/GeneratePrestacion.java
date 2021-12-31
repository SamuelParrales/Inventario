package devapp.inventario.entities.request;

import java.util.List;

public class GeneratePrestacion 
{
    private List<List<Integer>> productos;
    private double pago;
    private int idEmpleado;
    private int idCliente;
    private long fechaEntrega;
    private String direccionEntrega;
    private String garantia;
   


    public GeneratePrestacion(List<List<Integer>> productos, double pago, int idEmpleado, int idCliente,
            long fechaEntrega, String direccionEntrega, String garantia) {
        this.productos = productos;
        this.pago = pago;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.fechaEntrega = fechaEntrega;
        this.direccionEntrega = direccionEntrega;
        this.garantia = garantia;
    }
    public List<List<Integer>> getProductos() {
        return productos;
    }
    public void setProductos(List<List<Integer>> productos) {
        this.productos = productos;
    }
    public double getPago() {
        return pago;
    }
    public void setPago(double pago) {
        this.pago = pago;
    }
    public int getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public long getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(long fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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
