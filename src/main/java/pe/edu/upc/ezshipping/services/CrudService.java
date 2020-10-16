package pe.edu.upc.ezshipping.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
	T save(T entity) throws Exception; // devolver un objeto, si no manda un error para que no quede cargando

	T update(T entity) throws Exception;

	void deleteById(ID id) throws Exception;

	Optional<T> findById(ID id) throws Exception; // busca el objeto, si no hay no hay problema para que no devuelva
													// nulo

	List<T> findAll() throws Exception;
}
