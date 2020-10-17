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

import pe.edu.upc.ezshipping.models.entities.Paquete;
import pe.edu.upc.ezshipping.models.repositories.PaqueteRepository;

@Named
@ApplicationScoped
public class PaqueteRepositoryImpl implements PaqueteRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Paquete save(Paquete entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Paquete update(Paquete entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Paquete> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Paquete> findById(Integer id) throws Exception {
		Optional<Paquete> optional = Optional.empty();
		String qlString = "SELECT c FROM Paquete c WHERE c.id = ?1";
		TypedQuery<Paquete> query = em.createQuery(qlString, Paquete.class);
		query.setParameter(1, id);
		Paquete paquete = query.getResultList().stream().findFirst().orElse(null);
		if (paquete != null) {
			optional = Optional.of(paquete);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Paquete> findAll() throws Exception {
		List<Paquete> paquetes = new ArrayList<>();
		String qlString = "SELECT c FROM Paquete c";
		TypedQuery<Paquete> query = em.createQuery(qlString, Paquete.class);
		paquetes = query.getResultList();
		return paquetes;
	}

	@Override
	public Optional<Paquete> findByDescripcion(String descripcion) throws Exception {
		Optional<Paquete> optional = Optional.empty();
		String qlString = "SELECT c FROM Paquete c WHERE c.descripcion = ?1";
		TypedQuery<Paquete> query = em.createQuery(qlString, Paquete.class);
		query.setParameter(1, descripcion);
		Paquete paquete = query.getResultList().stream().findFirst().orElse(null);
		if (paquete != null) {
			optional = Optional.of(paquete);

		}
		return optional; // optional vacío instanciado al inicio
	}

}
