package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Persona;
import pe.edu.upc.ezshipping.models.repositories.PersonaRepository;
import pe.edu.upc.ezshipping.services.PersonaService;

@Named
@ApplicationScoped
public class PesonaServiceImpl implements PersonaService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PersonaRepository personaRepository;

	@Transactional
	@Override
	public Persona save(Persona entity) throws Exception {
		return personaRepository.save(entity);
	}

	@Transactional
	@Override
	public Persona update(Persona entity) throws Exception {
		return personaRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		personaRepository.deleteById(id);
	}

	@Override
	public Optional<Persona> findById(Integer id) throws Exception {
		return personaRepository.findById(id);
	}

	@Override
	public List<Persona> findAll() throws Exception {
		return personaRepository.findAll();
	}

	@Override
	public Optional<Persona> findByEmail(String email) throws Exception {
		return personaRepository.findByEmail(email);

	}

}
