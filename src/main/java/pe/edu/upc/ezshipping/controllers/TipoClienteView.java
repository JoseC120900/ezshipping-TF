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

import pe.edu.upc.ezshipping.models.entities.TipoCliente;
import pe.edu.upc.ezshipping.services.TipoClienteService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("tipoClienteView") // para las tablas de BD en la interface
@ViewScoped
public class TipoClienteView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TipoCliente> tipoClientes;
	// objeto asociado al formulario
	private TipoCliente tipoCliente;
	// objeto asociado al rowSelect del datatable
	private TipoCliente tipoClienteSelected;
	// objeto asociado al search
	private TipoCliente tipoClienteSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelTipoCliente;
	private String styleTableTipoCliente;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@Inject
	private TipoClienteService tipoClienteService;

	@PostConstruct
	private void init() {
		this.tipoClienteSearch = new TipoCliente();
		this.cleanForm();
		this.loadTipoClientes();
		this.action = Action.NONE;
		this.stateList();
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
		this.tipoCliente = new TipoCliente();
		this.tipoClienteSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newTipoCliente() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// metodo para grabar
	public void saveTipoCliente() {
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				if (this.action == Action.NEW)
					tipoClienteService.save(this.tipoCliente);
				else
					tipoClienteService.update(this.tipoCliente);
				cleanForm();
				loadTipoClientes();
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
	public void selectTipoCliente(SelectEvent<TipoCliente> e) {
		this.tipoClienteSelected = e.getObject();
		this.messageConfirmDialog = this.tipoClienteSelected.getNombre();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectTipoCliente(UnselectEvent<TipoCliente> e) {
		this.tipoClienteSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editTipoCliente() {
		if (this.tipoClienteSelected != null) {
			this.tipoCliente = this.tipoClienteSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelTipoCliente() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteTipoCliente() {
		if (this.tipoClienteSelected != null) {
			try {
				tipoClienteService.deleteById(this.tipoClienteSelected.getId());
				cleanForm();
				loadTipoClientes();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByNombre() {
		if (!this.tipoClienteSearch.getNombre().isEmpty()) {
			try {
				this.tipoClientes = new ArrayList<>();
				Optional<TipoCliente> optional = tipoClienteService.findByNombre(tipoClienteSearch.getNombre());
				if(optional.isPresent()) {
					this.tipoClientes.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyNombre() {
		this.tipoClienteSearch.setNombre("");
		loadTipoClientes();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelTipoCliente = "display:none;";
		this.styleTableTipoCliente = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelTipoCliente = "display:block;";
		this.styleTableTipoCliente = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelTipoCliente = "display:none;";
		this.styleTableTipoCliente = "display:block;";
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

	public List<TipoCliente> getTipoClientes() {
		return tipoClientes;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public TipoCliente getTipoClienteSelected() {
		return tipoClienteSelected;
	}

	public TipoClienteService getTipoClienteService() {
		return tipoClienteService;
	}

	public String getStylePanelTipoCliente() {
		return stylePanelTipoCliente;
	}

	public String getStyleTableTipoCliente() {
		return styleTableTipoCliente;
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

	public TipoCliente getTipoClienteSearch() {
		return tipoClienteSearch;
	}
}
