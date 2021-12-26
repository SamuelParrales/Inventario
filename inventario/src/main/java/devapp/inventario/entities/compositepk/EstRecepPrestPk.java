package devapp.inventario.entities.compositepk;

import java.io.Serializable;

public class EstRecepPrestPk implements Serializable{
    private long recepPrest;
    private boolean estado;
    public EstRecepPrestPk() {
    }
    public EstRecepPrestPk(long recepPrest, boolean estado) {
        this.recepPrest = recepPrest;
        this.estado = estado;
    }
    public long getRecepPrest() {
        return recepPrest;
    }
    public void setRecepPrest(long recepPrest) {
        this.recepPrest = recepPrest;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
}
