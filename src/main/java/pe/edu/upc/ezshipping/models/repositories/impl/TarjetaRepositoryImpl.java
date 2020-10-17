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

import pe.edu.upc.ezshipping.models.entities.Tarjeta;
import pe.edu.upc.ezshipping.models.repositories.TarjetaRepository;

@Named
@ApplicationScoped
public class TarjetaRepositoryImpl implements TarjetaRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Tarjeta save(Tarjeta entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Tarjeta update(Tarjeta entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Tarjeta> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Tarjeta> findById(Integer id) throws Exception {
		Optional<Tarjeta> optional = Optional.empty();
		String qlString = "SELECT c FROM Tarjeta c WHERE c.id = ?1";
		TypedQuery<Tarjeta> query = em.createQuery(qlString, Tarjeta.class);
		query.setParameter(1, id);
		Tarjeta tarjeta = query.getResultList().stream().findFirst().orElse(null);
		if (tarjeta != null) {
			optional = Optional.of(tarjeta);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Tarjeta> findAll() throws Exception {
		List<Tarjeta> tarjetas = new ArrayList<>();
		String qlString = "SELECT c FROM Tarjeta c";
		TypedQuery<Tarjeta> query = em.createQuery(qlString, Tarjeta.class);
		tarjetas = query.getResultList();
		return tarjetas;
	}

	@Override
	public Optional<Tarjeta> findByNumero(String nroTarjeta) throws Exception {
		Optional<Tarjeta> optional = Optional.empty();
		String qlString = "SELECT c FROM Tarjeta c WHERE c.nroTarjeta = ?1";
		TypedQuery<Tarjeta> query = em.createQuery(qlString, Tarjeta.class);
		query.setParameter(1, nroTarjeta);
		Tarjeta tarjeta = query.getResultList().stream().findFirst().orElse(null);
		if (tarjeta != null) {
			optional = Optional.of(tarjeta);

		}
		return optional; // optional vacío instanciado al inicio
	}

}
