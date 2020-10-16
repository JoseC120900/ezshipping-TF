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

import pe.edu.upc.ezshipping.models.entities.EstadoEnvio;
import pe.edu.upc.ezshipping.models.repositories.EstadoEnvioRepository;

@Named
@ApplicationScoped
public class EstadoEnvioRepositoryImpl implements EstadoEnvioRepository, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public EstadoEnvio save(EstadoEnvio entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public EstadoEnvio update(EstadoEnvio entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<EstadoEnvio> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<EstadoEnvio> findById(Integer id) throws Exception {
		Optional<EstadoEnvio> optional = Optional.empty();
		String qlString = "SELECT c FROM EstadoEnvio c WHERE c.id = ?1";
		TypedQuery<EstadoEnvio> query = em.createQuery(qlString, EstadoEnvio.class);
		query.setParameter(1, id);
		EstadoEnvio estadoEnvio = query.getSingleResult();
		if (estadoEnvio != null) {
			optional = Optional.of(estadoEnvio);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<EstadoEnvio> findAll() throws Exception {
		List<EstadoEnvio> estadoEnvios = new ArrayList<>();
		String qlString = "SELECT c FROM EstadoEnvio c";
		TypedQuery<EstadoEnvio> query = em.createQuery(qlString, EstadoEnvio.class);
		estadoEnvios = query.getResultList();
		return estadoEnvios;
	}
}
