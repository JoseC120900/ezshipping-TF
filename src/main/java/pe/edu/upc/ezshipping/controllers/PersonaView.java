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

import pe.edu.upc.ezshipping.models.entities.Persona;
import pe.edu.upc.ezshipping.services.PersonaService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("personaView") // para las tablas de BD en la interface
@ViewScoped
public class PersonaView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Persona> personas;
	// objeto asociado al formulario
	private Persona persona;
	// objeto asociado al rowSelect del datatable
	private Persona personaSelected;
	// objeto asociado al search
	private Persona personaSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelPersona;
	private String styleTablePersona;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@Inject
	private PersonaService personaService;

	@PostConstruct
	private void init() {
		this.personaSearch = new Persona();
		this.cleanForm();
		this.loadPersonas();
		this.action = Action.NONE;
		this.stateList();
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
		this.persona = new Persona();
		this.personaSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newPersona() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// metodo para grabar
	public void savePersona() {
		boolean uniqueEmail = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				// Para verificar que si el correo es único
				Optional<Persona> optional = personaService.findByEmail(personaSearch.getEmail());
				if (optional.isPresent()) {
					if (!optional.get().getId().equals(persona.getId())) {
						uniqueEmail = false;
					}
				}
				if (uniqueEmail == true) {
					if (this.action == Action.NEW)
						personaService.save(this.persona);
					else
						personaService.update(this.persona);
					cleanForm();
					loadPersonas();
					this.action = Action.NONE;
					this.stateList();
					this.addMessage("Guardado correctamente");
				} else {
					this.addMessage("El correo: " + persona.getEmail() + " ya se encuentra registrada");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectPersona(SelectEvent<Persona> e) {
		this.personaSelected = e.getObject();
		this.messageConfirmDialog = this.personaSelected.getNombre();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectPersona(UnselectEvent<Persona> e) {
		this.personaSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editPersona() {
		if (this.personaSelected != null) {
			this.persona = this.personaSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelPersona() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deletePersona() {
		if (this.personaSelected != null) {
			try {
				personaService.deleteById(this.personaSelected.getId());
				cleanForm();
				loadPersonas();
				this.action = Action.NONE;
				// this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByEmail() {
		if (!this.personaSearch.getEmail().isEmpty()) {
			try {
				this.personas = new ArrayList<>();
				Optional<Persona> optional = personaService.findByEmail(personaSearch.getEmail());
				if (optional.isPresent()) {
					this.personas.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyEmail() {
		this.personaSearch.setEmail("");
		loadPersonas();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelPersona = "display:none;";
		this.styleTablePersona = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelPersona = "display:block;";
		this.styleTablePersona = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelPersona = "display:none;";
		this.styleTablePersona = "display:block;";
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

	public List<Persona> getPersonas() {
		return personas;
	}

	public Persona getPersona() {
		return persona;
	}

	public Persona getPersonaSelected() {
		return personaSelected;
	}

	public String getStylePanelPersona() {
		return stylePanelPersona;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public String getStyleTablePersona() {
		return styleTablePersona;
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

	public Persona getPersonaSearch() {
		return personaSearch;
	}
}
