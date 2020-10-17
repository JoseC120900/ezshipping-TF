package pe.edu.upc.ezshipping.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.ezshipping.models.entities.Tarjeta;
import pe.edu.upc.ezshipping.models.repositories.TarjetaRepository;
import pe.edu.upc.ezshipping.services.TarjetaService;

@Named
@ApplicationScoped
public class TarjetaServiceImpl implements TarjetaService, Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private TarjetaRepository tarjetaRepository;
	
	@Transactional
	@Override
	public Tarjeta save(Tarjeta entity) throws Exception {
		return tarjetaRepository.save(entity);
	}

	@Transactional
	@Override
	public Tarjeta update(Tarjeta entity) throws Exception {
		return tarjetaRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		tarjetaRepository.deleteById(id);
		
	}

	@Override
	public Optional<Tarjeta> findById(Integer id) throws Exception {
		return tarjetaRepository.findById(id);
	}

	@Override
	public List<Tarjeta> findAll() throws Exception {
		return tarjetaRepository.findAll();
	}

	@Override
	public Optional<Tarjeta> findByNumero(String nroTarjeta) throws Exception {
		return tarjetaRepository.findByNumero(nroTarjeta);
	}

}
