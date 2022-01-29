package devapp.inventario.dto;

import java.util.List;

public class RecepcionDto 
{
    Integer idEmpleado;
    List<ProductoPresDto> productos;
    Double valorPagado;
    public RecepcionDto() {
    }

    public RecepcionDto(Integer idEmpleado, List<ProductoPresDto> productos, Double valorPagado) {
        this.idEmpleado = idEmpleado;
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

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
}
