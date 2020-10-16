package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Estado;
import pe.edu.upc.ezshipping.models.repositories.EstadoRepository;
import pe.edu.upc.ezshipping.services.EstadoService;

@Named
@ApplicationScoped
public class EstadoServiceImpl implements EstadoService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoRepository estadoRepository;

	@Transactional
	@Override
	public Estado save(Estado entity) throws Exception {
		return estadoRepository.save(entity);
	}

	@Transactional
	@Override
	public Estado update(Estado entity) throws Exception {
		return estadoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		estadoRepository.deleteById(id);
	}

	@Override
	public Optional<Estado> findById(Integer id) throws Exception {
		return estadoRepository.findById(id);
	}

	@Override
	public List<Estado> findAll() throws Exception {
		return estadoRepository.findAll();
	}

	@Override
	public Optional<Estado> findByNombre(String nombre) throws Exception {
		return estadoRepository.findByNombre(nombre);
	}

}
