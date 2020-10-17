package pe.edu.upc.ezshipping.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "envios")
public class Envio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "monto", length = 10, nullable = false)
	private Float monto;
	
	@Column(name = "nombre_destinatario", length = 20, nullable = false)
	private String nombreDestinatario;
	
	@Column(name = "direccion_origen", length = 100, nullable = false)
	private String direccionOrigen;
	
	@Column(name = "direccion_destino", length = 100, nullable = false)
	private String direccionDestino;
	
	@ManyToOne
	@JoinColumn(name = "trabajador_id")
	private Trabajador trabajador;

	@Transient
	private Integer trabajadorId;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Transient
	private Integer clienteId;

	@ManyToOne
	@JoinColumn(name = "tarjeta_id")
	private Tarjeta tarjeta;

	@Transient
	private Integer tarjetaId;

	@ManyToOne
	@JoinColumn(name = "reclamo_id")
	private Reclamo reclamo;

	@Transient
	private Integer reclamoId;

	@OneToMany(mappedBy = "envio")
	private List<EstadoEnvio> estadoEnvios;

	@OneToMany(mappedBy = "envio")
	private List<Paquete> paquetes;

	public Envio() {
		estadoEnvios = new ArrayList<EstadoEnvio>();
		paquetes = new ArrayList<Paquete>();
		this.clienteId = 0;
		this.trabajadorId = 0;
		this.tarjetaId = 0;
		this.reclamoId = 0;

	}
	
	public String getIdString() {
		return Integer.toString(id);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreDestinatario() {
		return nombreDestinatario;
	}

	public void setNombreDestinatario(String nombreDestinatario) {
		this.nombreDestinatario = nombreDestinatario;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
		if (this.trabajador != null) {
			this.trabajadorId = this.trabajador.getId();
		}
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getDireccionOrigen() {
		return direccionOrigen;
	}

	public String getDireccionDestino() {
		return direccionDestino;
	}

	public void setDireccionOrigen(String direccionOrigen) {
		this.direccionOrigen = direccionOrigen;
	}

	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		if (this.cliente != null) {
			this.clienteId = this.cliente.getId();
		}
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
		if (this.tarjeta != null) {
			this.tarjetaId = this.tarjeta.getId();
		}
	}

	public Reclamo getReclamo() {
		return reclamo;
	}

	public void setReclamo(Reclamo reclamo) {
		this.reclamo = reclamo;
		if (this.reclamo != null) {
			this.reclamoId = this.reclamo.getId();
		}
	}

	public List<EstadoEnvio> getEstadoEnvios() {
		return estadoEnvios;
	}

	public void setEstadoEnvios(List<EstadoEnvio> estadoEnvios) {
		this.estadoEnvios = estadoEnvios;
	}

	public List<Paquete> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<Paquete> paquetes) {
		this.paquetes = paquetes;
	}

	public Integer getTrabajadorId() {
		if (this.trabajadorId <= 0 && this.trabajador != null) {
			this.trabajadorId = this.trabajador.getId();
		}
		return trabajadorId;
	}

	public void setTrabajadorId(Integer trabajadorId) {
		this.trabajadorId = trabajadorId;
	}

	public Integer getClienteId() {
		if (this.clienteId <= 0 && this.cliente != null) {
			this.clienteId = this.cliente.getId();
		}
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getTarjetaId() {
		if (this.tarjetaId <= 0 && this.tarjeta != null) {
			this.tarjetaId = this.tarjeta.getId();
		}
		return tarjetaId;
	}

	public void setTarjetaId(Integer tarjetaId) {
		this.tarjetaId = tarjetaId;
	}

	public Integer getReclamoId() {
		if (this.reclamoId <= 0 && this.reclamo != null) {
			this.reclamoId = this.reclamo.getId();
		}
		return reclamoId;
	}

	public void setReclamoId(Integer reclamoId) {
		this.reclamoId = reclamoId;
	}
}
