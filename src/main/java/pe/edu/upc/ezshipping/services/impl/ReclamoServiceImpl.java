package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Reclamo;
import pe.edu.upc.ezshipping.models.repositories.ReclamoRepository;
import pe.edu.upc.ezshipping.services.ReclamoService;

@Named
@ApplicationScoped
public class ReclamoServiceImpl implements ReclamoService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamoRepository reclamoRepository;

	@Transactional
	@Override
	public Reclamo save(Reclamo entity) throws Exception {
		return reclamoRepository.save(entity);
	}

	@Transactional
	@Override
	public Reclamo update(Reclamo entity) throws Exception {
		return reclamoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		reclamoRepository.deleteById(id);
	}

	@Override
	public Optional<Reclamo> findById(Integer id) throws Exception {
		return reclamoRepository.findById(id);
	}

	@Override
	public List<Reclamo> findAll() throws Exception {
		return reclamoRepository.findAll();
	}

	@Override
	public Optional<Reclamo> findByDescripcion(String descripcion) throws Exception {
		return reclamoRepository.findByDescripcion(descripcion);
	}

}
