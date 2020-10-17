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

import pe.edu.upc.ezshipping.models.entities.Trabajador;
import pe.edu.upc.ezshipping.models.repositories.TrabajadorRepository;

@Named
@ApplicationScoped
public class TrabajadorRepositoryImpl implements TrabajadorRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Trabajador save(Trabajador entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Trabajador update(Trabajador entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Trabajador> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Trabajador> findById(Integer id) throws Exception {
		Optional<Trabajador> optional = Optional.empty();
		String qlString = "SELECT c FROM Trabajador c WHERE c.id = ?1";
		TypedQuery<Trabajador> query = em.createQuery(qlString, Trabajador.class);
		query.setParameter(1, id);
		Trabajador trabajador = query.getResultList().stream().findFirst().orElse(null);
		if (trabajador != null) {
			optional = Optional.of(trabajador);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Trabajador> findAll() throws Exception {
		List<Trabajador> trabajadores = new ArrayList<>();
		String qlString = "SELECT c FROM Trabajador c";
		TypedQuery<Trabajador> query = em.createQuery(qlString, Trabajador.class);
		trabajadores = query.getResultList();
		return trabajadores;
	}
}
