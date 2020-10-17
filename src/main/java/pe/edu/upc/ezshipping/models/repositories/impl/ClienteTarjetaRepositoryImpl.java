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

import pe.edu.upc.ezshipping.models.entities.ClienteTarjeta;
import pe.edu.upc.ezshipping.models.repositories.ClienteTarjetaRepository;

@Named
@ApplicationScoped
public class ClienteTarjetaRepositoryImpl implements ClienteTarjetaRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public ClienteTarjeta save(ClienteTarjeta entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public ClienteTarjeta update(ClienteTarjeta entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<ClienteTarjeta> optional = findById(id);
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<ClienteTarjeta> findById(Integer id) throws Exception {
		Optional<ClienteTarjeta> optional = Optional.empty();
		String qlString = "SELECT c FROM ClienteTarjeta c WHERE c.id = ?1";
		TypedQuery<ClienteTarjeta> query = em.createQuery(qlString, ClienteTarjeta.class);
		query.setParameter(1, id);
		ClienteTarjeta clienteTarjeta = query.getSingleResult();
		if (clienteTarjeta != null) {
			optional = Optional.of(clienteTarjeta);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<ClienteTarjeta> findAll() throws Exception {
		List<ClienteTarjeta> clienteTarjetas = new ArrayList<>();
		String qlString = "SELECT c FROM ClienteTarjeta c";
		TypedQuery<ClienteTarjeta> query = em.createQuery(qlString, ClienteTarjeta.class);
		clienteTarjetas = query.getResultList();
		return clienteTarjetas;
	}

}
