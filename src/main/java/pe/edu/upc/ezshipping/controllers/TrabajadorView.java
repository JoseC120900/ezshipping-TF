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

import pe.edu.upc.ezshipping.models.entities.Persona;
import pe.edu.upc.ezshipping.models.entities.Trabajador;
import pe.edu.upc.ezshipping.services.PersonaService;
import pe.edu.upc.ezshipping.services.TrabajadorService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("trabajadorView") // para las tablas de BD en la interface
@ViewScoped
public class TrabajadorView implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Trabajador> trabajadores;
	// objeto asociado al formulario
	private Trabajador trabajador;
	// objeto asociado al rowSelect del datatable
	private Trabajador trabajadorSelected;

	private Action action;
	// Style for Panel and Data
	private String stylePanelTrabajador;
	private String styleTableTrabajador;

	private List<Persona> personas;
	private Persona personaSelected;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@Inject
	private TrabajadorService trabajadorService;

	@Inject
	private PersonaService personaService;

	@PostConstruct
	private void init() {
		this.cleanForm();
		this.loadTrabajadores();
		this.loadPersonas();
		this.action = Action.NONE;
		this.stateList();
	}

	public void loadTrabajadores() {
		try {
			this.trabajadores = trabajadorService.findAll();
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

	public void cleanForm() {
		this.trabajador = new Trabajador();
		this.trabajadorSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newTrabajador() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// Funciona cuando se cambia la persona
	public void changePersona() {
		if (this.trabajador.getPersona() != null) {
			if (!this.trabajador.getPersona().getId().equals(this.trabajador.getPersonaId())) {
				loadPersona(this.trabajador.getPersonaId());
				this.trabajador.setPersona(this.personaSelected);
			}
		} // Cuando es una nueva persona
		else {
			loadPersona(this.trabajador.getPersonaId());
			this.trabajador.setPersona(this.personaSelected);
		}
	}

	// metodo para grabar
	public void saveTrabajador() {
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				changePersona();
				if (this.action == Action.NEW)
					trabajadorService.save(this.trabajador);
				else
					trabajadorService.update(this.trabajador);
				cleanForm();
				loadTrabajadores();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectTrabajador(SelectEvent<Trabajador> e) {
		this.trabajadorSelected = e.getObject();
		this.messageConfirmDialog = this.trabajadorSelected.getPersona().getNombre();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectTrabajador(UnselectEvent<Trabajador> e) {
		this.trabajadorSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editTrabajador() {
		if (this.trabajadorSelected != null) {
			this.trabajador = this.trabajadorSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelTrabajador() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteTrabajador() {
		if (this.trabajadorSelected != null) {
			try {
				trabajadorService.deleteById(this.trabajadorSelected.getId());
				cleanForm();
				loadTrabajadores();
				this.action = Action.NONE;
				this.stateList();
				// this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	
	//BUSCAR:
	//public void findById() 
	//public void cleanbyId() el setter debe ser string 

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelTrabajador = "display:none;";
		this.styleTableTrabajador = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelTrabajador = "display:block;";
		this.styleTableTrabajador = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelTrabajador = "display:none;";
		this.styleTableTrabajador = "display:block;";
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

	public List<Trabajador> getTrabajadores() {
		return trabajadores;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public String getStylePanelTrabajador() {
		return stylePanelTrabajador;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public TrabajadorService getTrabajadorService() {
		return trabajadorService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public Persona getPersonaSelected() {
		return personaSelected;
	}

	public String getStyleTableTrabajador() {
		return styleTableTrabajador;
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
