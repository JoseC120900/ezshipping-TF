package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Paquete;
import pe.edu.upc.ezshipping.models.repositories.PaqueteRepository;
import pe.edu.upc.ezshipping.services.PaqueteService;

@Named
@ApplicationScoped
public class PaqueteServiceImpl implements PaqueteService, Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PaqueteRepository paqueteRepository;
	
	@Transactional
	@Override
	public Paquete save(Paquete entity) throws Exception {
		return paqueteRepository.save(entity);
	}

	@Transactional
	@Override
	public Paquete update(Paquete entity) throws Exception {
		return paqueteRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		paqueteRepository.deleteById(id);
	}

	@Override
	public Optional<Paquete> findById(Integer id) throws Exception {
		return paqueteRepository.findById(id);
	}

	@Override
	public List<Paquete> findAll() throws Exception {
		return paqueteRepository.findAll();
	}

	@Override
	public Optional<Paquete> findByDescripcion(String descripcion) throws Exception {
		return paqueteRepository.findByDescripcion(descripcion);
	}
}
