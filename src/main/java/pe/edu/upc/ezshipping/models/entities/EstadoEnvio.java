package pe.edu.upc.ezshipping.models.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "estado_envios")
@IdClass(EstadoEnvioId.class)
public class EstadoEnvio {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "estado_id", nullable = false)
	private Estado estado;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Envio envio;
	
	@Column(name = "fecha_estadoPedido", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaEstadoPedido;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	public Date getFechaEstadoPedido() {
		return fechaEstadoPedido;
	}

	public void setFechaEstadoPedido(Date fechaEstadoPedido) {
		this.fechaEstadoPedido = fechaEstadoPedido;
	}
	
	
}
