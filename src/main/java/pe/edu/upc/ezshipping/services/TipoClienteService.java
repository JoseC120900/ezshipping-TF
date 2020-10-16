package pe.edu.upc.ezshipping.services;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.TipoCliente;

public interface TipoClienteService extends CrudService<TipoCliente, Integer> {
	Optional<TipoCliente> findByNombre(String nombre) throws Exception;
}
