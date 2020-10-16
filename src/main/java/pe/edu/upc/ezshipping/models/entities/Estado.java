package pe.edu.upc.ezshipping.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "estados")
public class Estado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", length = 20, nullable = false)
	private String nombre;
	
	@OneToMany(mappedBy = "estado")
	private List<EstadoEnvio> estadoEnvios;
	
	public Estado() {
		estadoEnvios = new ArrayList<EstadoEnvio>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<EstadoEnvio> getEstadoEnvios() {
		return estadoEnvios;
	}

	public void setEstadoEnvios(List<EstadoEnvio> estadoEnvios) {
		this.estadoEnvios = estadoEnvios;
	}
	
}
