package pe.edu.upc.ezshipping.models.repositories;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	Optional<Estado> findByNombre(String nombre) throws Exception;
}
