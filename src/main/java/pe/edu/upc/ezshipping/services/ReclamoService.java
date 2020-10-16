package pe.edu.upc.ezshipping.services;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Reclamo;

public interface ReclamoService extends CrudService<Reclamo, Integer> {
	Optional<Reclamo> findByDescripcion(String descripcion) throws Exception;
}
