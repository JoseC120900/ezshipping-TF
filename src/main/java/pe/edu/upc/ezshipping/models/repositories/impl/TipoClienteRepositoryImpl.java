package pe.edu.upc.ezshipping.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.ezshipping.models.entities.TipoCliente;
import pe.edu.upc.ezshipping.models.repositories.TipoClienteRepository;

@Named
@ApplicationScoped
public class TipoClienteRepositoryImpl implements TipoClienteRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public TipoCliente save(TipoCliente entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public TipoCliente update(TipoCliente entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<TipoCliente> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<TipoCliente> findById(Integer id) throws Exception {
		Optional<TipoCliente> optional = Optional.empty();
		String qlString = "SELECT c FROM TipoCliente c WHERE c.id = ?1";
		TypedQuery<TipoCliente> query = em.createQuery(qlString, TipoCliente.class);
		query.setParameter(1, id);
		TipoCliente tipoCliente = query.getResultList().stream().findFirst().orElse(null);
		if (tipoCliente != null) {
			optional = Optional.of(tipoCliente);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<TipoCliente> findAll() throws Exception {
		List<TipoCliente> tipoClientes = new ArrayList<>();
		String qlString = "SELECT c FROM TipoCliente c";
		TypedQuery<TipoCliente> query = em.createQuery(qlString, TipoCliente.class);
		tipoClientes = query.getResultList();
		return tipoClientes;
	}

	@Override
	public Optional<TipoCliente> findByNombre(String nombre) throws Exception {
		Optional<TipoCliente> optional = Optional.empty();
		String qlString = "SELECT c FROM TipoCliente c WHERE c.nombre = ?1";
		TypedQuery<TipoCliente> query = em.createQuery(qlString, TipoCliente.class);
		query.setParameter(1, nombre);
		TipoCliente tipoCliente = query.getResultList().stream().findFirst().orElse(null);
		if (tipoCliente != null) {
			optional = Optional.of(tipoCliente);
		}
		return optional;
	}
}
