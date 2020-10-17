package pe.edu.upc.ezshipping.models.entities;

import java.io.Serializable;
import java.util.Objects;

public class ClienteTarjetaId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer cliente;
	private Integer tarjeta;
	
	public ClienteTarjetaId() {	
	}
	
	public ClienteTarjetaId(Integer cliente, Integer tarjeta) {
		super();
		this.cliente = cliente;
		this.tarjeta = tarjeta;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cliente, tarjeta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
        if (obj == null || getClass() != obj.getClass()) 
        	return false;
        ClienteTarjetaId clienteTarjetaId = (ClienteTarjetaId) obj;
        if (this.cliente != clienteTarjetaId.cliente) 
        	return false;
        return this.tarjeta == clienteTarjetaId.tarjeta;
	}
}
