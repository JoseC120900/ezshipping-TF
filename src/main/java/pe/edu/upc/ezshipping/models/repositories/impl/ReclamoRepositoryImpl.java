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

import pe.edu.upc.ezshipping.models.entities.Reclamo;
import pe.edu.upc.ezshipping.models.repositories.ReclamoRepository;

@Named
@ApplicationScoped
public class ReclamoRepositoryImpl implements ReclamoRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Reclamo save(Reclamo entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Reclamo update(Reclamo entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Reclamo> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Reclamo> findById(Integer id) throws Exception {
		Optional<Reclamo> optional = Optional.empty();
		String qlString = "SELECT c FROM Reclamo c WHERE c.id = ?1";
		TypedQuery<Reclamo> query = em.createQuery(qlString, Reclamo.class);
		query.setParameter(1, id);
		Reclamo reclamo = query.getResultList().stream().findFirst().orElse(null);
		if (reclamo != null) {
			optional = Optional.of(reclamo);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Reclamo> findAll() throws Exception {
		List<Reclamo> reclamos = new ArrayList<>();
		String qlString = "SELECT c FROM Reclamo c";
		TypedQuery<Reclamo> query = em.createQuery(qlString, Reclamo.class);
		reclamos = query.getResultList();
		return reclamos;
	}

	@Override
	public Optional<Reclamo> findByDescripcion(String descripcion) throws Exception {
		Optional<Reclamo> optional = Optional.empty();
		String qlString = "SELECT c FROM Reclamo c WHERE c.descripcion = ?1";
		TypedQuery<Reclamo> query = em.createQuery(qlString, Reclamo.class);
		query.setParameter(1, descripcion);
		Reclamo reclamo = query.getResultList().stream().findFirst().orElse(null);
		if (reclamo != null) {
			optional = Optional.of(reclamo);

		}
		return optional; // optional vacío instanciado al inicio
	}

}
