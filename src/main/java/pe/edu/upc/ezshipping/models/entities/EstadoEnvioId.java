package pe.edu.upc.ezshipping.models.entities;

import java.io.Serializable;
import java.util.Objects;

public class EstadoEnvioId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer estado;
	private Integer envio;
	
	public EstadoEnvioId() {	
	}

	public EstadoEnvioId(Integer estado, Integer pedido) {
		super();
		this.estado = estado;
		this.envio = pedido;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	public Integer getEnvio() {
		return envio;
	}

	public void setEnvio(Integer envio) {
		this.envio = envio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado,envio);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
        if (obj == null || getClass() != obj.getClass()) 
        	return false;
        EstadoEnvioId estadoPedidoId = (EstadoEnvioId) obj;
        if (this.estado != estadoPedidoId.estado) 
        	return false;
        return this.envio == estadoPedidoId.envio;
	}
	
}
