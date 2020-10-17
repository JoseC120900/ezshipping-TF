package pe.edu.upc.ezshipping.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import pe.edu.upc.ezshipping.models.entities.Cliente;
import pe.edu.upc.ezshipping.models.entities.Persona;
import pe.edu.upc.ezshipping.models.entities.TipoCliente;
import pe.edu.upc.ezshipping.services.ClienteService;
import pe.edu.upc.ezshipping.services.PersonaService;
import pe.edu.upc.ezshipping.services.TipoClienteService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("clienteView") // para las tablas de BD en la interface
@ViewScoped
public class ClienteView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Cliente> clientes;
	// objeto asociado al formulario
	private Cliente cliente;
	// objeto asociado al rowSelect del datatable
	private Cliente clienteSelected;
	// objeto asociado al search
	private Cliente clienteSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelCliente;
	private String styleTableCliente;

	private List<Persona> personas;
	private Persona personaSelected;

	private List<TipoCliente> tipoClientes;
	private TipoCliente tipoClienteSelected;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@Inject
	private ClienteService clienteService;

	@Inject
	private PersonaService personaService;

	@Inject
	private TipoClienteService tipoClienteService;

	@PostConstruct
	private void init() {
		this.clienteSearch = new Cliente();
		this.cleanForm();
		this.loadClientes();
		this.loadTipoClientes();
		this.loadPersonas();
		this.action = Action.NONE;
		this.stateList();
	}

	public void loadClientes() {
		try {
			this.clientes = clienteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo persona
	public void loadPersona(Integer id) {
		try {
			this.personaSelected = (personaService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadPersonas() {
		try {
			this.personas = personaService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo tipoCliente
	public void loadTipoCliente(Integer id) {
		try {
			this.tipoClienteSelected = (tipoClienteService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadTipoClientes() {
		try {
			this.tipoClientes = tipoClienteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	public void cleanForm() {
		this.cliente = new Cliente();
		this.clienteSelected = null;
	}

	// metodo cuando se hace clic en boton nuevo
	public void newCliente() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// Funciona cuando se cambia la persona
	public void changePersona() {
		if (this.cliente.getPersona() != null) {
			if (!this.cliente.getPersona().getId().equals(this.cliente.getPersonaId())) {
				loadPersona(this.cliente.getPersonaId());
				this.cliente.setPersona(this.personaSelected);
			}
		} // Cuando es una nueva persona
		else {
			loadPersona(this.cliente.getPersonaId());
			this.cliente.setPersona(this.personaSelected);
		}
	}

	// Funciona cuando se cambia el tipoCliente
	public void changeTipoCliente() {
		if (this.cliente.getTipoCliente() != null) {
			if (!this.cliente.getTipoCliente().getId().equals(this.cliente.getTipoClienteId())) {
				loadTipoCliente(this.cliente.getTipoClienteId());
				this.cliente.setTipoCliente(this.tipoClienteSelected);
			}
		} // Cuando es un nuevo tipoCliente
		else {
			loadTipoCliente(this.cliente.getTipoClienteId());
			this.cliente.setTipoCliente(this.tipoClienteSelected);
		}
	}

	// metodo para grabar
	public void saveCliente() {
		boolean uniqueDNI = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				// Para verificar que si el DNI es único
				Optional<Cliente> optional = clienteService.findByDNI(clienteSearch.getDNI());
				if (optional.isPresent()) {
					if (!optional.get().getId().equals(cliente.getId())) {
						uniqueDNI = false;
					}
				}
				if (uniqueDNI == true) {
					changePersona();
					changeTipoCliente();
					if (this.action == Action.NEW)
						clienteService.save(this.cliente);
					else
						clienteService.update(this.cliente);
					cleanForm();
					loadClientes();
					this.action = Action.NONE;
					this.stateList();
					this.addMessage("Guardado correctamente");
				} else {
					this.addMessage("El DNI: " + cliente.getDNI() + " ya se encuentra registrada");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectCliente(SelectEvent<Cliente> e) {
		this.clienteSelected = e.getObject();
		this.messageConfirmDialog = this.clienteSelected.getPersona().getNombre();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectCliente(UnselectEvent<Cliente> e) {
		this.clienteSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editCliente() {
		if (this.clienteSelected != null) {
			this.cliente = this.clienteSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelCliente() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteCliente() {
		if (this.clienteSelected != null) {
			try {
				clienteService.deleteById(this.clienteSelected.getId());
				cleanForm();
				loadClientes();
				this.action = Action.NONE;
				this.stateList();
				// this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByDNI() {
		if (!this.clienteSearch.getDNI().isEmpty()) {
			try {
				this.clientes = new ArrayList<>();
				Optional<Cliente> optional = clienteService.findByDNI(clienteSearch.getDNI());
				if (optional.isPresent()) {
					this.clientes.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyDNI() {
		this.clienteSearch.setDNI("");
		loadClientes();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelCliente = "display:none;";
		this.styleTableCliente = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelCliente = "display:block;";
		this.styleTableCliente = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelCliente = "display:none;";
		this.styleTableCliente = "display:block;";
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

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getStylePanelCliente() {
		return stylePanelCliente;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public List<TipoCliente> getTipoClientes() {
		return tipoClientes;
	}

	public TipoCliente getTipoClienteSelected() {
		return tipoClienteSelected;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public TipoClienteService getTipoClienteService() {
		return tipoClienteService;
	}

	public Cliente getClienteSearch() {
		return clienteSearch;
	}

	public String getStyleTableCliente() {
		return styleTableCliente;
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
