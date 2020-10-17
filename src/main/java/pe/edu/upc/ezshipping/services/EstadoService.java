package pe.edu.upc.ezshipping.services;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Estado;

public interface EstadoService extends CrudService<Estado, Integer> {
	Optional<Estado> findByNombre(String nombre) throws Exception;
}
