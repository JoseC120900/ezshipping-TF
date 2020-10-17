package pe.edu.upc.ezshipping.services;

import java.util.List;

import pe.edu.upc.ezshipping.models.entities.Envio;

public interface EnvioService extends CrudService<Envio, Integer> {
	List<Envio> findByDireccionOrigen(String direccionOrigen) throws Exception;

	List<Envio> findByDireccionDestino(String direccionDestino) throws Exception;

}
