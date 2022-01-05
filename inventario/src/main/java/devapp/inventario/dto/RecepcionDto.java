package devapp.inventario.dto;

import java.util.List;

public class RecepcionDto 
{
    
    List<ProductoPresDto> productos;
    Double valorPagado;
    public RecepcionDto() {
    }
    public RecepcionDto(List<ProductoPresDto> productos,Double valorPagado) {
        this.productos = productos;
        this.valorPagado = valorPagado;
        
    }
    public List<ProductoPresDto> getProductos() {
        return productos;
    }
    public void setProductos(List<ProductoPresDto> productos) {
        this.productos = productos;
    }
    public Double getValorPagado() {
        return valorPagado;
    }
    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }
    
    
}
