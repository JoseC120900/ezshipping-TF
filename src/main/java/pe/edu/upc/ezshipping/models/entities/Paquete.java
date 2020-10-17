package pe.edu.upc.ezshipping.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "paquetes")
public class Paquete {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "alto", length = 10, nullable = false)
	private Float alto;

	@Column(name = "ancho", length = 10, nullable = false)
	private Float ancho;

	@Column(name = "largo", length = 10, nullable = false)
	private Float largo;

	@Column(name = "precio_unitario", length = 10, nullable = false)
	private Float precioUnitario;

	@Column(name = "descripcion", length = 40, nullable = false)
	private String descripcion;

	@Column(name = "peso", length = 10, nullable = false)
	private Float peso;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Envio envio;

	@Transient
	private Integer envioId;

	public Paquete() {
		this.envioId = 0;
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

	public Float getAlto() {
		return alto;
	}

	public void setAlto(Float alto) {
		this.alto = alto;
	}

	public Float getAncho() {
		return ancho;
	}

	public void setAncho(Float ancho) {
		this.ancho = ancho;
	}

	public Float getLargo() {
		return largo;
	}

	public void setLargo(Float largo) {
		this.largo = largo;
	}

	public Float getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
		if (this.envio != null) {
			this.envioId = this.envio.getId();
		}
	}

	public Integer getEnvioId() {
		if (this.envioId <= 0 && this.envio != null) {
			this.envioId = this.envio.getId();
		}
		return envioId;
	}

	public void setEnvioId(Integer envioId) {
		this.envioId = envioId;
	}

}
