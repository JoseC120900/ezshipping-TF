package pe.edu.upc.ezshipping.services;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Paquete;

public interface PaqueteService extends CrudService<Paquete, Integer> {
	Optional<Paquete> findByDescripcion(String descripcion) throws Exception;
}
