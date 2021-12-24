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
@Table(name="detrecdes")
public class Detallerecdes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idderecdes;
	
	
	
	private int cantidadproducto;
	private float subtotal;
	
	@JoinColumn(name="idproducto")
	@ManyToOne(cascade = CascadeType.MERGE)		//Hija de cliente
	private Producto producto;
	
	
	@JoinColumn(name="id_rep_des")
	@ManyToOne(cascade = CascadeType.MERGE)		//Hija de cliente
	private Recepciondespacho recepDesp;


	public int getCantidadproducto() {
		return cantidadproducto;
	}


	public void setCantidadproducto(int cantidadproducto) {
		this.cantidadproducto = cantidadproducto;
	}


	public float getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}


	public int getIdderecdes() {
		return idderecdes;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Recepciondespacho getRecepDesp() {
		return recepDesp;
	}


	public void setRecepDesp(Recepciondespacho recepDesp) {
		this.recepDesp = recepDesp;
	}
	
	
	
	
}
