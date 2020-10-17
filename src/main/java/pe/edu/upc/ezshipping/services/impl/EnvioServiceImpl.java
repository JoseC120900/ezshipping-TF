package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Envio;
import pe.edu.upc.ezshipping.models.repositories.EnvioRepository;
import pe.edu.upc.ezshipping.services.EnvioService;

@Named
@ApplicationScoped
public class EnvioServiceImpl implements EnvioService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EnvioRepository pedidoRepository;

	@Transactional
	@Override
	public Envio save(Envio entity) throws Exception {
		return pedidoRepository.save(entity);
	}

	@Transactional
	@Override
	public Envio update(Envio entity) throws Exception {
		return pedidoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		pedidoRepository.deleteById(id);
	}

	@Override
	public Optional<Envio> findById(Integer id) throws Exception {
		return pedidoRepository.findById(id);
	}

	@Override
	public List<Envio> findAll() throws Exception {
		return pedidoRepository.findAll();
	}

	@Override
	public List<Envio> findByDireccionOrigen(String direccionOrigen) throws Exception {
		return pedidoRepository.findByDireccionOrigen(direccionOrigen);

	}

	@Override
	public List<Envio> findByDireccionDestino(String direccionDestino) throws Exception {
		return pedidoRepository.findByDireccionDestino(direccionDestino);
	}

}
