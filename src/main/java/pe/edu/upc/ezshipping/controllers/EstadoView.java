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

import pe.edu.upc.ezshipping.models.entities.Estado;
import pe.edu.upc.ezshipping.services.EstadoService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("estadoView") // para las tablas de BD en la interface
@ViewScoped
public class EstadoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Estado> estados;
	// objeto asociado al formulario
	private Estado estado;
	// objeto asociado al rowSelect del datatable
	private Estado estadoSelected;
	// objeto asociado al search
	private Estado estadoSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelEstado;
	private String styleTableEstado;

	@Inject
	private EstadoService estadoService;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@PostConstruct
	private void init() {
		this.estadoSearch = new Estado();
		this.cleanForm();
		this.loadEstados();
		this.action = Action.NONE;
		this.stateList();
	}

	public void loadEstados() {
		try {
			this.estados = estadoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	public void cleanForm() {
		this.estado = new Estado();
		this.estadoSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newEstado() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// metodo para grabar
	public void saveEstado() {
		boolean uniqueNombre = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				// Para verificar que si el nombre es único
				Optional<Estado> optional = estadoService.findByNombre(estadoSearch.getNombre());
				if (optional.isPresent()) {
					if (!optional.get().getId().equals(estado.getId())) {
						uniqueNombre = false;
					}
				}
				if (uniqueNombre == true) {
					if (this.action == Action.NEW)
						estadoService.save(this.estado);
					else
						estadoService.update(this.estado);
					cleanForm();
					loadEstados();
					this.action = Action.NONE;
					this.stateList();
					this.addMessage("Guardado correctamente");
				} else {
					this.addMessage("El estado: " + estado.getNombre() + " ya se encuentra registrada");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectEstado(SelectEvent<Estado> e) {
		this.estadoSelected = e.getObject();
		this.messageConfirmDialog = this.estadoSelected.getNombre();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectEstado(UnselectEvent<Estado> e) {
		this.estadoSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editEstado() {
		if (this.estadoSelected != null) {
			this.estado = this.estadoSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelEstado() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteEstado() {
		if (this.estadoSelected != null) {
			try {
				estadoService.deleteById(this.estadoSelected.getId());
				cleanForm();
				loadEstados();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByNombre() {
		if (!this.estadoSearch.getNombre().isEmpty()) {
			try {
				this.estados = new ArrayList<>();
				Optional<Estado> optional = estadoService.findByNombre(estadoSearch.getNombre());
				if (optional.isPresent()) {
					this.estados.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyNombre() {
		this.estadoSearch.setNombre("");
		loadEstados();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelEstado = "display:none;";
		this.styleTableEstado = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelEstado = "display:block;";
		this.styleTableEstado = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelEstado = "display:none;";
		this.styleTableEstado = "display:block;";
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

	public List<Estado> getEstados() {
		return estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public Estado getEstadoSelected() {
		return estadoSelected;
	}

	public EstadoService getEstadoService() {
		return estadoService;
	}

	public String getStylePanelEstado() {
		return stylePanelEstado;
	}

	public Estado getEstadoSearch() {
		return estadoSearch;
	}

	public String getStyleTableEstado() {
		return styleTableEstado;
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
