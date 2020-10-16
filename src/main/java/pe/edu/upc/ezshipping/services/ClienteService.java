package pe.edu.upc.ezshipping.services;

import java.util.Optional;

import pe.edu.upc.ezshipping.models.entities.Cliente;

public interface ClienteService extends CrudService<Cliente, Integer> {
	Optional<Cliente> findByDNI(String DNI) throws Exception;
}
