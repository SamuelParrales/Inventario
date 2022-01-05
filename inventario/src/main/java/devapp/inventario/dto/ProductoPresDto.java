package devapp.inventario.dto;

public class ProductoPresDto {
    private int id;
    private int cant;
    
    public ProductoPresDto() {
    }
 
    public ProductoPresDto(int id, int cant) {
        this.id = id;
        this.cant = cant;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

}
