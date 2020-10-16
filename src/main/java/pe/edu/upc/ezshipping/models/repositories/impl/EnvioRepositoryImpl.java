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

import pe.edu.upc.ezshipping.models.entities.Envio;
import pe.edu.upc.ezshipping.models.repositories.EnvioRepository;

@Named
@ApplicationScoped
public class EnvioRepositoryImpl implements EnvioRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Envio save(Envio entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Envio update(Envio entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// Declara y asigna el resultado de la busqueda
		Optional<Envio> optional = findById(id);
		// Verifica si optional contiene un objeto
		if (optional.isPresent()) {
			// remuevo el objeto
			em.remove(optional.get());
		}

	}

	@Override
	public Optional<Envio> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Envio> optional = Optional.empty();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Envio c WHERE c.id = ?1";
		// Crear la consulta
		TypedQuery<Envio> query = em.createQuery(qlString, Envio.class);
		// Estableciendo los paremetros: id
		query.setParameter(1, id);
		// Obtener el resultado de la consulta
		Envio envio = query.getResultList().stream().findFirst().orElse(null);
		// Verificar la existencia del objeto
		if (envio != null) {
			// Agregando el objeto pedido al Optional
			optional = Optional.of(envio);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Envio> findAll() throws Exception {
		// Declara la variable a retornar
		List<Envio> envios = new ArrayList<>();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Envio c";
		// Crear la consulta
		TypedQuery<Envio> query = em.createQuery(qlString, Envio.class);
		// Obtener el resultado de la consulta
		envios = query.getResultList();
		return envios;
	}

	@Override
	public List<Envio> findByDireccionOrigen(String direccionOrigen) throws Exception {
		List<Envio> envios = new ArrayList<>();
		String qlString = "SELECT c FROM Envio c WHERE c.direccionOrigen LIKE ?1";
		TypedQuery<Envio> query = em.createQuery(qlString, Envio.class);
		query.setParameter(1, "%" + direccionOrigen.toUpperCase() + "%");
		envios = query.getResultList();
		return envios;
	}

	@Override
	public List<Envio> findByDireccionDestino(String direccionDestino) throws Exception {
		List<Envio> envios = new ArrayList<>();
		String qlString = "SELECT c FROM Envio c WHERE c.direccionDestino LIKE ?1";
		TypedQuery<Envio> query = em.createQuery(qlString, Envio.class);
		query.setParameter(1, "%" + direccionDestino.toUpperCase() + "%");
		envios = query.getResultList();
		return envios;
	}

}
