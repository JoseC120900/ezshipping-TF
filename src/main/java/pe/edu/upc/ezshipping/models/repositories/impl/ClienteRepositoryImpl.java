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

import pe.edu.upc.ezshipping.models.entities.Cliente;
import pe.edu.upc.ezshipping.models.repositories.ClienteRepository;

@Named
@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EZShippingPU")
	private EntityManager em;

	@Override
	public Cliente save(Cliente entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Cliente update(Cliente entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// Declara y asigna el resultado de la busqueda
		Optional<Cliente> optional = findById(id);
		// Verifica si optional contiene un objeto
		if (optional.isPresent()) {
			// remuevo el objeto
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Cliente> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Cliente> optional = Optional.empty();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Cliente c WHERE c.id = ?1";
		// Crear la consulta
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		// Estableciendo los paremetros: id
		query.setParameter(1, id);
		// Obtener el resultado de la consulta
		Cliente cliente = query.getResultList().stream().findFirst().orElse(null);
		// Verificar la existencia del objeto
		if (cliente != null) {
			// Agregando el objeto cliente al Optional
			optional = Optional.of(cliente);
		}

		return optional; // optional vacío instanciado al inicio
	}

	@Override
	public List<Cliente> findAll() throws Exception {
		// Declara la variable a retornar
		List<Cliente> clientes = new ArrayList<>();
		// Elaborar el JPQL
		String qlString = "SELECT c FROM Cliente c";
		// Crear la consulta
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		// Obtener el resultado de la consulta
		clientes = query.getResultList();
		return clientes;
	}

	@Override
	public Optional<Cliente> findByDNI(String DNI) throws Exception {
		Optional<Cliente> optional = Optional.empty();
		String qlString = "SELECT c FROM Cliente c WHERE c.DNI = ?1";
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, DNI);
		Cliente dni = query.getResultList().stream().findFirst().orElse(null);
		if (dni != null) {
			optional = Optional.of(dni);
		}
		return optional;
	}

}
