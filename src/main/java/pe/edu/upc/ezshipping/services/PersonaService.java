package pe.edu.upc.ezshipping.services;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Persona;

public interface PersonaService extends CrudService<Persona, Integer> {

	Optional<Persona> findByEmail(String email) throws Exception;

}
