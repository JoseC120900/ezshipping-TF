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

import pe.edu.upc.ezshipping.models.entities.Reclamo;
import pe.edu.upc.ezshipping.services.ReclamoService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("reclamoView") // para las tablas de BD en la interface
@ViewScoped
public class ReclamoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Reclamo> reclamos;
	// objeto asociado al formulario
	private Reclamo reclamo;
	// objeto asociado al rowSelect del datatable
	private Reclamo reclamoSelected;
	// objeto asociado al search
	private Reclamo reclamoSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelReclamo;
	private String styleTableReclamo;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@Inject
	private ReclamoService reclamoService;

	@PostConstruct
	private void init() {
		this.reclamoSearch = new Reclamo();
		this.cleanForm();
		this.loadReclamos();
		this.action = Action.NONE;
		this.stateList();
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
		this.reclamo = new Reclamo();
		this.reclamoSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newReclamo() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// metodo para grabar
	public void saveReclamo() {
		boolean uniqueDescripcion = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				// Para verificar que si el nombre es único
				Optional<Reclamo> optional = reclamoService.findByDescripcion(reclamoSearch.getDescripcion());
				if (optional.isPresent()) {
					if (!optional.get().getId().equals(reclamo.getId())) {
						uniqueDescripcion = false;
					}
				}
				if (uniqueDescripcion == true) {
					if (this.action == Action.NEW)
						reclamoService.save(this.reclamo);
					else
						reclamoService.update(this.reclamo);
					cleanForm();
					loadReclamos();
					this.action = Action.NONE;
					this.stateList();
					this.addMessage("Guardado correctamente");
				} else {
					this.addMessage("El estado: " + reclamo.getDescripcion() + " ya se encuentra registrada");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectReclamo(SelectEvent<Reclamo> e) {
		this.reclamoSelected = e.getObject();
		this.messageConfirmDialog = this.reclamoSelected.getIdString();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectReclamo(UnselectEvent<Reclamo> e) {
		this.reclamoSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editReclamo() {
		if (this.reclamoSelected != null) {
			this.reclamo = this.reclamoSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelReclamo() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteReclamo() {
		if (this.reclamoSelected != null) {
			try {
				reclamoService.deleteById(this.reclamoSelected.getId());
				cleanForm();
				loadReclamos();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByDescripcion() {
		if (!this.reclamoSearch.getDescripcion().isEmpty()) {
			try {
				this.reclamos = new ArrayList<>();
				Optional<Reclamo> optional = reclamoService.findByDescripcion(reclamoSearch.getDescripcion());
				if (optional.isPresent()) {
					this.reclamos.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyDescripcion() {
		this.reclamoSearch.setDescripcion("");
		loadReclamos();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelReclamo = "display:none;";
		this.styleTableReclamo = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelReclamo = "display:block;";
		this.styleTableReclamo = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelReclamo = "display:none;";
		this.styleTableReclamo = "display:block;";
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

	public Reclamo getReclamoSearch() {
		return reclamoSearch;
	}

	public String getStyleTableReclamo() {
		return styleTableReclamo;
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

	public List<Reclamo> getReclamos() {
		return reclamos;
	}

	public Reclamo getReclamo() {
		return reclamo;
	}

	public Reclamo getReclamoSelected() {
		return reclamoSelected;
	}

	public ReclamoService getReclamoService() {
		return reclamoService;
	}

	public String getStylePanelReclamo() {
		return stylePanelReclamo;
	}

}
