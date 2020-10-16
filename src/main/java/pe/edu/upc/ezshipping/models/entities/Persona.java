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
@Table(name = "personas")
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", length = 20, nullable = false)
	private String nombre;

	@Column(name = "apellido", length = 20, nullable = false)
	private String apellido;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "fecha_nacimiento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	@OneToMany(mappedBy = "persona")
	private List<Cliente> cliente;

	@OneToMany(mappedBy = "persona")
	private List<Trabajador> trabajador;

	public Persona() {
		cliente = new ArrayList<Cliente>();
		trabajador = new ArrayList<Trabajador>();
	}

	public String getIdString() {
		return Integer.toString(id);
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getEmail() {
		return email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public List<Cliente> getCliente() {
		return cliente;
	}

	public List<Trabajador> getTrabajador() {
		return trabajador;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}

	public void setTrabajador(List<Trabajador> trabajador) {
		this.trabajador = trabajador;
	}

}
