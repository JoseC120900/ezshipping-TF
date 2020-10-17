package pe.edu.upc.ezshipping.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reclamos")
public class Reclamo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "descripcion", length = 40, nullable = false)
	private String descripcion;

	@Column(name = "fecha_reclamo", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaReclamo;

	@OneToMany(mappedBy = "reclamo")
	private List<Envio> envios;

	public Reclamo() {
		envios = new ArrayList<Envio>();
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaReclamo() {
		return fechaReclamo;
	}

	public void setFechaReclamo(Date fechaReclamo) {
		this.fechaReclamo = fechaReclamo;
	}

	public List<Envio> getEnvios() {
		return envios;
	}

	public void setEnvios(List<Envio> envios) {
		this.envios = envios;
	}

}
