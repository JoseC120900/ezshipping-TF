package pe.edu.upc.ezshipping.models.entities;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "trabajadores")
public class Trabajador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "persona_id")
	private Persona persona;

	@Column(name = "fecha_contratacion", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaContratacion;

	@Column(name = "courier", nullable = false)
	private Boolean courier;

	@OneToMany(mappedBy = "trabajador")
	private List<Envio> envios;

	@Transient
	private Integer personaId;

	public Trabajador() {
		envios = new ArrayList<Envio>();
		this.personaId = 0;
	}

	public String getIdString() {
		return Integer.toString(id);
	}
	
	public Integer getId() {
		return id;
	}

	public Persona getPersona() {
		return persona;
	}

	public Date getFechaContratacion() {
		return fechaContratacion;
	}

	public Boolean getCourier() {
		return courier;
	}

	public List<Envio> getEnvios() {
		return envios;
	}

	public Integer getPersonaId() {
		if (this.personaId <= 0 && this.persona != null) {
			this.personaId = this.persona.getId();
		}
		return personaId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}

	public void setCourier(Boolean courier) {
		this.courier = courier;
	}

	public void setEnvios(List<Envio> envios) {
		this.envios = envios;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
}
