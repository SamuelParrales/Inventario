package devapp.inventario.entities.compositepk;

import java.io.Serializable;

public class EstRecepPrestPk implements Serializable{
    private long recepPrest;
    private int estado;
    public EstRecepPrestPk() {
    }
    public EstRecepPrestPk(long recepPrest, int estado) {
        this.recepPrest = recepPrest;
        this.estado = estado;
    }
    public long getRecepPrest() {
        return recepPrest;
    }
    public void setRecepPrest(long recepPrest) {
        this.recepPrest = recepPrest;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
    
}
