package devapp.inventario.dto;

import java.util.List;

public class ReservacionClientDto 
{
    private List<ProductoPresDto> productos;
    private double valorPagado;
    private int idCliente;
    private String dirEntrega;
    public ReservacionClientDto() {
    }
    public ReservacionClientDto(List<ProductoPresDto> productos, double valorPagado, int idCliente,
            String dirEntrega) {
        this.productos = productos;
        this.valorPagado = valorPagado;
        this.idCliente = idCliente;
        this.dirEntrega = dirEntrega;
    }
    public List<ProductoPresDto> getProductos() {
        return productos;
    }
    public void setProductos(List<ProductoPresDto> productos) {
        this.productos = productos;
    }
    public double getValorPagado() {
        return valorPagado;
    }
    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getDirEntrega() {
        return dirEntrega;
    }
    public void setDirEntrega(String dirEntrega) {
        this.dirEntrega = dirEntrega;
    }
    
}
