package devapp.inventario.dto;

import java.util.List;

public class PrestacionEmplDto 
{
    private List<ProductoPresDto> productos;
    private Double valorPagado;
    private Integer idEmpleado;
    private Integer idCliente;
    private String dirEntrega;
    private String garantia;
    private Long fechaCaducida;
    private boolean reservacion;
    
    public PrestacionEmplDto() {
    }
    public PrestacionEmplDto(List<ProductoPresDto> productos, Double valorPagado, Integer idEmpleado, Integer idCliente,
            String dirEntrega, String garantia, Long fechaCaducida, boolean reservacion) {
        this.productos = productos;
        this.valorPagado = valorPagado;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.dirEntrega = dirEntrega;
        this.garantia = garantia;
        this.fechaCaducida = fechaCaducida;
        this.reservacion = reservacion;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getDirEntrega() {
        return dirEntrega;
    }

    public void setDirEntrega(String dirEntrega) {
        this.dirEntrega = dirEntrega;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Long getFechaCaducida() {
        return fechaCaducida;
    }

    public void setFechaCaducida(Long fechaCaducida) {
        this.fechaCaducida = fechaCaducida;
    }
    public boolean isReservacion() {
        return reservacion;
    }
    public void setReservacion(boolean reservacion) {
        this.reservacion = reservacion;
    }
}
