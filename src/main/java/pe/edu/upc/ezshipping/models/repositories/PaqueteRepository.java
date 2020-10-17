package pe.edu.upc.ezshipping.models.repositories;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Paquete;

public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {
	Optional<Paquete> findByDescripcion(String descripcion) throws Exception;
}
