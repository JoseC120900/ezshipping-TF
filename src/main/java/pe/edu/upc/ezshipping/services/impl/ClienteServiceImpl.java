package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Cliente;
import pe.edu.upc.ezshipping.models.repositories.ClienteRepository;
import pe.edu.upc.ezshipping.services.ClienteService;

@Named
@ApplicationScoped
public class ClienteServiceImpl implements ClienteService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository clienteRepository;

	@Transactional
	@Override
	public Cliente save(Cliente entity) throws Exception {
		return clienteRepository.save(entity);
	}

	@Transactional
	@Override
	public Cliente update(Cliente entity) throws Exception {
		return clienteRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		clienteRepository.deleteById(id);
	}

	@Override
	public Optional<Cliente> findById(Integer id) throws Exception {
		return clienteRepository.findById(id);
	}

	@Override
	public List<Cliente> findAll() throws Exception {
		return clienteRepository.findAll();
	}

	@Override
	public Optional<Cliente> findByDNI(String DNI) throws Exception {
		return clienteRepository.findByDNI(DNI);
	}

}
