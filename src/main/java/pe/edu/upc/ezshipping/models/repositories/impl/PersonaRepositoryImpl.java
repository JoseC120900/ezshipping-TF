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

import pe.edu.upc.ezshipping.models.entities.Persona;
import pe.edu.upc.ezshipping.models.repositories.PersonaRepository;

@Named
@ApplicationScoped
public class PersonaRepositoryImpl implements PersonaRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Persona save(Persona entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Persona update(Persona entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// Declara y asigna el resultado de la busqueda
		Optional<Persona> optional = findById(id);
		// Verifica si optional contiene un objeto
		if (optional.isPresent()) {
			// remuevo el objeto
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Persona> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Persona> optional = Optional.empty();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Persona c WHERE c.id = ?1";
		// Crear la consulta
		TypedQuery<Persona> query = em.createQuery(qlString, Persona.class);
		// Estableciendo los paremetros: id
		query.setParameter(1, id);
		// Obtener el resultado de la consulta
		Persona persona = query.getResultList().stream().findFirst().orElse(null);
		// Verificar la existencia del objeto
		if (persona != null) {
			// Agregando el objeto pedido al Optional
			optional = Optional.of(persona);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Persona> findAll() throws Exception {
		// Declara la variable a retornar
		List<Persona> envios = new ArrayList<>();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Persona c";
		// Crear la consulta
		TypedQuery<Persona> query = em.createQuery(qlString, Persona.class);
		// Obtener el resultado de la consulta
		envios = query.getResultList();
		return envios;
	}

	@Override
	public Optional<Persona> findByEmail(String email) throws Exception {
		Optional<Persona> optional = Optional.empty();
		String qlString = "SELECT c FROM Persona c WHERE c.email = ?1";
		TypedQuery<Persona> query = em.createQuery(qlString, Persona.class);
		query.setParameter(1, email);
		Persona persona = query.getResultList().stream().findFirst().orElse(null);
		if (persona != null) {
			optional = Optional.of(persona);
		}

		return optional;
	}

}
