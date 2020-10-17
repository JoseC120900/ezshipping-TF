package pe.edu.upc.ezshipping.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "persona_id")
	private Persona persona;

	@ManyToOne
	@JoinColumn(name = "tipo_cliente_id")
	private TipoCliente tipoCliente;
	
	@Column(name = "dni", length = 8, nullable = false)
	private String DNI;

	@OneToMany(mappedBy = "cliente")
	private List<ClienteTarjeta> clienteTarjetas;

	@OneToMany(mappedBy = "cliente")
	private List<Envio> envios;

	@Transient
	private Integer personaId;

	@Transient
	private Integer tipoClienteId;

	public Cliente() {
		clienteTarjetas = new ArrayList<ClienteTarjeta>();
		envios = new ArrayList<Envio>();
		this.personaId = 0;
		this.tipoClienteId = 0;
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
	
	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
		if (this.tipoCliente != null) {
			this.tipoClienteId = this.tipoCliente.getId();
		}
	}

	public List<ClienteTarjeta> getClienteTarjetas() {
		return clienteTarjetas;
	}

	public void setClienteTarjetas(List<ClienteTarjeta> clienteTarjetas) {
		this.clienteTarjetas = clienteTarjetas;
	}

	public List<Envio> getEnvios() {
		return envios;
	}

	public void setEnvios(List<Envio> envios) {
		this.envios = envios;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
		if (this.persona != null) {
			this.personaId = this.persona.getId();
		}
	}

	public Integer getTipoClienteId() {
		if (this.tipoClienteId <= 0 && this.tipoCliente != null) {
			this.tipoClienteId = this.tipoCliente.getId();
		}
		return tipoClienteId;
	}

	public void setTipoClienteId(Integer tipoClienteId) {
		this.tipoClienteId = tipoClienteId;
	}

	public Integer getPersonaId() {
		if (this.personaId <= 0 && this.persona != null) {
			this.personaId = this.persona.getId();
		}
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
}
