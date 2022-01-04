package devapp.inventario.dto;

import java.util.List;

public class ReservacionClientDto 
{
    private List<ProductoPresDto> productos;
    private double valorPagado;
    private String dirEntrega;
    public ReservacionClientDto() {
    }
    public ReservacionClientDto(List<ProductoPresDto> productos, double valorPagado,
            String dirEntrega) {
        this.productos = productos;
        this.valorPagado = valorPagado;
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

    public String getDirEntrega() {
        return dirEntrega;
    }
    public void setDirEntrega(String dirEntrega) {
        this.dirEntrega = dirEntrega;
    }
    
}
