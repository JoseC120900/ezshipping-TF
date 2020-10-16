package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.TipoCliente;
import pe.edu.upc.ezshipping.models.repositories.TipoClienteRepository;
import pe.edu.upc.ezshipping.services.TipoClienteService;

@Named
@ApplicationScoped
public class TipoClienteServiceImpl implements TipoClienteService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoClienteRepository tipoClienteRepository;
	
	@Transactional
	@Override
	public TipoCliente save(TipoCliente entity) throws Exception {
		return tipoClienteRepository.save(entity);
	}

	@Transactional
	@Override
	public TipoCliente update(TipoCliente entity) throws Exception {
		return tipoClienteRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		tipoClienteRepository.deleteById(id);
	}

	@Override
	public Optional<TipoCliente> findById(Integer id) throws Exception {
		return tipoClienteRepository.findById(id);
	}

	@Override
	public List<TipoCliente> findAll() throws Exception {
		return tipoClienteRepository.findAll();
	}

	@Override
	public Optional<TipoCliente> findByNombre(String nombre) throws Exception {
		return tipoClienteRepository.findByNombre(nombre);
	}

}
