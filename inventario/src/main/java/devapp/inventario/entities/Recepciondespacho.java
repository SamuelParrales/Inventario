package devapp.inventario.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="recepciondespacho")
public class Recepciondespacho {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrecepciondespacho;
	private float valortotal;
	private float valorgarantia;
	
	
	@JoinColumn(name="idcliente")
	@ManyToOne(cascade =CascadeType.MERGE)		//Hija de cliente
	private Cliente cliente;

	
	
	
	public Recepciondespacho(float valortotal, float valorgarantia, Cliente cliente) {
		super();
		this.valortotal = valortotal;
		this.valorgarantia = valorgarantia;
		this.cliente = cliente;
	}
	
	public Recepciondespacho()
	{
		
	}
	public float getValortotal() {
		return valortotal;
	}
	public void setValortotal(float valortotal) {
		this.valortotal = valortotal;
	}
	public float getValorgarantia() {
		return valorgarantia;
	}
	public void setValorgarantia(float valorgarantia) {
		this.valorgarantia = valorgarantia;
	}
	public int getIdrecepciondespacho() {
		return idrecepciondespacho;
	}

	@Override
	public String toString() {
		return "Recepciondespacho [idrecepciondespacho=" + idrecepciondespacho + ", valortotal=" + valortotal
				+ ", valorgarantia=" + valorgarantia + ", cliente=" + cliente.getIdcliente() + "]";
	}
	
	
	
}
