package pe.edu.upc.ezshipping.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import pe.edu.upc.ezshipping.models.entities.Cliente;
import pe.edu.upc.ezshipping.models.entities.Envio;
import pe.edu.upc.ezshipping.models.entities.Reclamo;
import pe.edu.upc.ezshipping.models.entities.Tarjeta;
import pe.edu.upc.ezshipping.models.entities.Trabajador;
import pe.edu.upc.ezshipping.services.ClienteService;
import pe.edu.upc.ezshipping.services.EnvioService;
import pe.edu.upc.ezshipping.services.ReclamoService;
import pe.edu.upc.ezshipping.services.TarjetaService;
import pe.edu.upc.ezshipping.services.TrabajadorService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("envioView") // para las tablas de BD en la interface
@ViewScoped
public class EnvioView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Envio> envios;
	// objeto asociado al formulario
	private Envio envio;
	// objeto asociado al rowSelect del datatable
	private Envio envioSelected;
	// objeto asociado al search
	private Envio envioSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelEnvio;
	private String styleTableEnvio;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	private List<Trabajador> trabajadores;
	private Trabajador trabajadorSelected;

	private List<Cliente> clientes;
	private Cliente clienteSelected;

	private List<Tarjeta> tarjetas;
	private Tarjeta tarjetaSelected;

	private List<Reclamo> reclamos;
	private Reclamo reclamoSelected;

	@Inject
	private EnvioService envioService;

	@Inject
	private TrabajadorService trabajadorService;

	@Inject
	private ClienteService clienteService;

	@Inject
	private TarjetaService tarjetaService;

	@Inject
	private ReclamoService reclamoService;

	@PostConstruct
	private void init() {
		this.envioSearch = new Envio();
		this.cleanForm();
		this.loadEnvios();
		this.loadTrabajadores();
		this.loadClientes();
		this.loadTarjetas();
		this.loadReclamos();
		this.action = Action.NONE;
		this.stateList();
	}

	public void loadEnvios() {
		try {
			this.envios = envioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo trabajador
	public void loadTrabajador(Integer id) {
		try {
			this.trabajadorSelected = (trabajadorService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadTrabajadores() {
		try {
			this.trabajadores = trabajadorService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo cliente
	public void loadCliente(Integer id) {
		try {
			this.clienteSelected = (clienteService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadClientes() {
		try {
			this.clientes = clienteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo tarjeta
	public void loadTarjeta(Integer id) {
		try {
			this.tarjetaSelected = (tarjetaService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadTarjetas() {
		try {
			this.tarjetas = tarjetaService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo reclamo
	public void loadReclamo(Integer id) {
		try {
			this.reclamoSelected = (reclamoService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadReclamos() {
		try {
			this.reclamos = reclamoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	public void cleanForm() {
		this.envio = new Envio();
		this.envioSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newEnvio() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// Funciona cuando se cambia el/la trabajador
	public void changeTrabajador() {
		if (this.envio.getTrabajador() != null) {
			if (!this.envio.getTrabajador().getId().equals(this.envio.getTrabajadorId())) {
				loadTrabajador(this.envio.getTrabajadorId());
				this.envio.setTrabajador(this.trabajadorSelected);
			}
		} // Cuando es un nuevo Envío
		else {
			loadTrabajador(this.envio.getTrabajadorId());
			this.envio.setTrabajador(this.trabajadorSelected);
		}
	}

	// Funciona cuando se cambia el/la cliente
	public void changeCliente() {
		if (this.envio.getCliente() != null) {
			if (!this.envio.getCliente().getId().equals(this.envio.getClienteId())) {
				loadCliente(this.envio.getClienteId());
				this.envio.setCliente(this.clienteSelected);
			}
		} // Cuando es un nuevo Envío
		else {
			loadCliente(this.envio.getClienteId());
			this.envio.setCliente(this.clienteSelected);
		}
	}

	// Funciona cuando se cambia la tarjeta
	public void changeTarjeta() {
		if (this.envio.getTarjeta() != null) {
			if (!this.envio.getTarjeta().getId().equals(this.envio.getTarjetaId())) {
				loadTarjeta(this.envio.getTarjetaId());
				this.envio.setTarjeta(this.tarjetaSelected);
			}
		} // Cuando es un nuevo Envío
		else {
			loadTarjeta(this.envio.getTarjetaId());
			this.envio.setTarjeta(this.tarjetaSelected);
		}
	}

	// Funciona cuando se cambia el reclamo
	public void changeReclamo() {
		if (this.envio.getReclamo() != null) {
			if (!this.envio.getReclamo().getId().equals(this.envio.getReclamoId())) {
				loadReclamo(this.envio.getReclamoId());
				this.envio.setReclamo(this.reclamoSelected);
			}
		} // Cuando es un nuevo Envío
		else {
			loadReclamo(this.envio.getReclamoId());
			this.envio.setReclamo(this.reclamoSelected);
		}
	}

	// metodo para grabar
	public void saveEnvio() {
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				changeTrabajador();
				changeCliente();
				changeTarjeta();
				changeReclamo();
				if (this.action == Action.NEW)
					envioService.save(this.envio);
				else
					envioService.update(this.envio);
				cleanForm();
				loadEnvios();
				this.action = Action.NONE;
				this.stateList();
				this.addMessage("Guardado correctamente");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectEnvio(SelectEvent<Envio> e) {
		this.envioSelected = e.getObject();
		this.messageConfirmDialog = this.envioSelected.getIdString();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectEnvio(UnselectEvent<Envio> e) {
		this.envioSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editEnvio() {
		if (this.envioSelected != null) {
			this.envio = this.envioSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelEnvio() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteEnvio() {
		if (this.envioSelected != null) {
			try {
				envioService.deleteById(this.envioSelected.getId());
				cleanForm();
				loadEnvios();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByDireccionOrigen() {
		if (!this.envioSearch.getDireccionOrigen().isEmpty()) {
			try {
				this.envios = envioService.findByDireccionOrigen(envioSearch.getDireccionOrigen());
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyDireccionOrigen() {
		this.envioSearch.setDireccionOrigen("");
		loadEnvios();
		this.stateList();
	}

	public void findByDireccionDestino() {
		if (!this.envioSearch.getDireccionDestino().isEmpty()) {
			try {
				this.envios = envioService.findByDireccionDestino(envioSearch.getDireccionDestino());
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyDireccionDestino() {
		this.envioSearch.setDireccionDestino("");
		loadEnvios();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelEnvio = "display:none;";
		this.styleTableEnvio = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelEnvio = "display:block;";
		this.styleTableEnvio = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelEnvio = "display:none;";
		this.styleTableEnvio = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = false;
		this.disabledButtonEliminar = false;
	}

	// Metodo que muestra los mensajes
	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public List<Envio> getEnvios() {
		return envios;
	}

	public Envio getEnvio() {
		return envio;
	}

	public String getStylePanelEnvio() {
		return stylePanelEnvio;
	}

	public List<Trabajador> getTrabajadores() {
		return trabajadores;
	}

	public Trabajador getTrabajadorSelected() {
		return trabajadorSelected;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Cliente getClienteSelected() {
		return clienteSelected;
	}

	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public Tarjeta getTarjetaSelected() {
		return tarjetaSelected;
	}

	public List<Reclamo> getReclamos() {
		return reclamos;
	}

	public Reclamo getReclamoSelected() {
		return reclamoSelected;
	}

	public EnvioService getEnvioService() {
		return envioService;
	}

	public TrabajadorService getTrabajadorService() {
		return trabajadorService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public TarjetaService getTarjetaService() {
		return tarjetaService;
	}

	public ReclamoService getReclamoService() {
		return reclamoService;
	}

	public Envio getEnvioSearch() {
		return envioSearch;
	}

	public String getStyleTableEnvio() {
		return styleTableEnvio;
	}

	public boolean isDisabledButtonNuevo() {
		return disabledButtonNuevo;
	}

	public boolean isDisabledButtonGuardar() {
		return disabledButtonGuardar;
	}

	public boolean isDisabledButtonCancelar() {
		return disabledButtonCancelar;
	}

	public boolean isDisabledButtonEditar() {
		return disabledButtonEditar;
	}

	public boolean isDisabledButtonEliminar() {
		return disabledButtonEliminar;
	}

	public String getMessageConfirmDialog() {
		return messageConfirmDialog;
	}
}
