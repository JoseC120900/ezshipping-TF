package pe.edu.upc.ezshipping.models.repositories;

import java.util.List;

import pe.edu.upc.ezshipping.models.entities.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {
	List<Envio> findByDireccionOrigen(String direccionOrigen) throws Exception;

	List<Envio> findByDireccionDestino(String direccionDestino) throws Exception;
}
