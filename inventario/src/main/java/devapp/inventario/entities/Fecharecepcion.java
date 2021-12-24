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
@Table(name="fecharecepcion")
public class Fecharecepcion {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfecharecepcion;
	private String estado;
	private String fecha;
	
	@JoinColumn(name="id_rep_des")
	@ManyToOne(cascade = CascadeType.MERGE)		//Hija de cliente
	private Recepciondespacho recepDesp;

	@JoinColumn(name="idempleado")
	@ManyToOne(cascade = CascadeType.MERGE)		//Hija de cliente
	private Empleado empleado;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getIdfecharecepcion() {
		return idfecharecepcion;
	}
	
	
	
	
	
	
	
}
