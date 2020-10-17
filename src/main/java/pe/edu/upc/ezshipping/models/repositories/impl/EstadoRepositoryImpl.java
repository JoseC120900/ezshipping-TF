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

import pe.edu.upc.ezshipping.models.entities.Estado;
import pe.edu.upc.ezshipping.models.repositories.EstadoRepository;

@Named
@ApplicationScoped
public class EstadoRepositoryImpl implements EstadoRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Estado save(Estado entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Estado update(Estado entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Estado> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Estado> findById(Integer id) throws Exception {
		Optional<Estado> optional = Optional.empty();
		String qlString = "SELECT c FROM Estado c WHERE c.id = ?1";
		TypedQuery<Estado> query = em.createQuery(qlString, Estado.class);
		query.setParameter(1, id);
		Estado estado = query.getResultList().stream().findFirst().orElse(null);
		if (estado != null) {
			optional = Optional.of(estado);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Estado> findAll() throws Exception {
		List<Estado> estados = new ArrayList<>();
		String qlString = "SELECT c FROM Estado c";
		TypedQuery<Estado> query = em.createQuery(qlString, Estado.class);
		estados = query.getResultList();
		return estados;
	}

	@Override
	public Optional<Estado> findByNombre(String nombre) throws Exception {
		Optional<Estado> optional = Optional.empty();
		String qlString = "SELECT c FROM Estado c WHERE c.nombre = ?1";
		TypedQuery<Estado> query = em.createQuery(qlString, Estado.class);
		query.setParameter(1, nombre);
		Estado estado = query.getResultList().stream().findFirst().orElse(null);
		if (estado != null) {
			optional = Optional.of(estado);

		}
		return optional; // optional vacío instanciado al inicio
	}

}
