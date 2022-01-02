package devapp.inventario.dto;

public class ProductoPresDto {
    private int id;
    private int cantPrestada;
    
    public ProductoPresDto() {
    }
    public ProductoPresDto(int id, int cantPrestada) {
        this.id = id;
        this.cantPrestada = cantPrestada;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCantPrestada() {
        return cantPrestada;
    }
    public void setCantPrestada(int cantPrestada) {
        this.cantPrestada = cantPrestada;
    }

}
