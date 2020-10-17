package pe.edu.upc.ezshipping.models.repositories;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

	Optional<Persona> findByEmail(String email) throws Exception;

}
