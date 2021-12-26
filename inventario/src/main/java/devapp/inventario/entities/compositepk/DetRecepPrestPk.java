package devapp.inventario.entities.compositepk;

import java.io.Serializable;

public class DetRecepPrestPk implements Serializable{
    private long recepPrest;
    private int producto;

    public DetRecepPrestPk() {
    }

    public DetRecepPrestPk(long recepPrest, int producto) {
        this.recepPrest = recepPrest;
        this.producto = producto;
    }

    public long getRecepPrest() {
        return recepPrest;
    }

    public void setRecepPrest(long recepPrest) {
        this.recepPrest = recepPrest;
    }



    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }
    



    
    
    
    
}
