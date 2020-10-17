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

import pe.edu.upc.ezshipping.models.entities.Envio;
import pe.edu.upc.ezshipping.models.entities.Paquete;
import pe.edu.upc.ezshipping.services.EnvioService;
import pe.edu.upc.ezshipping.services.PaqueteService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("paqueteView") // para las tablas de BD en la interface
@ViewScoped
public class PaqueteView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Paquete> paquetes;
	// objeto asociado al formulario
	private Paquete paquete;
	// objeto asociado al rowSelect del datatable
	private Paquete paqueteSelected;
	// objeto asociado al search
	private Paquete paqueteSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelPaquete;
	private String styleTablePaquete;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	private List<Envio> envios;
	private Envio envioSelected;

	@Inject
	private PaqueteService paqueteService;

	@Inject
	private EnvioService envioService;

	@PostConstruct
	private void init() {
		this.paqueteSearch = new Paquete();
		this.cleanForm();
		this.loadPaquetes();
		this.loadEnvios();
		this.action = Action.NONE;
		this.stateList();
	}

	public void loadPaquetes() {
		try {
			this.paquetes = paqueteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	// va a cargar el id del atributo envío
	public void loadEnvio(Integer id) {
		try {
			this.envioSelected = (envioService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void loadEnvios() {
		try {
			this.envios = envioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	public void cleanForm() {
		this.paquete = new Paquete();
		this.paqueteSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newPaquete() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// Funciona cuando se cambia el envío
	public void changeEnvio() {
		if (this.paquete.getEnvio() != null) {
			if (!this.paquete.getEnvio().getId().equals(this.paquete.getEnvioId())) {
				loadEnvio(this.paquete.getEnvioId());
				this.paquete.setEnvio(this.envioSelected);
			}
		} // Cuando es un nuevo Envío
		else {
			loadEnvio(this.paquete.getEnvioId());
			this.paquete.setEnvio(this.envioSelected);
		}
	}

	// metodo para grabar
	public void savePaquete() {
		boolean uniqueDescripcion = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				// Para verificar que si la descripcion es única
				Optional<Paquete> optional = paqueteService.findByDescripcion(paqueteSearch.getDescripcion());
				if (optional.isPresent()) {
					if (!optional.get().getId().equals(paquete.getId())) {
						uniqueDescripcion = false;
					}
				}
				if (uniqueDescripcion == true) {
					changeEnvio();
					if (this.action == Action.NEW)
						paqueteService.save(this.paquete);
					else
						paqueteService.update(this.paquete);
					cleanForm();
					loadPaquetes();
					this.action = Action.NONE;
					this.stateList();
					this.addMessage("Guardado correctamente");
				} else {
					this.addMessage("La descripcion: " + paquete.getDescripcion() + " ya se encuentra registrada");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectPaquete(SelectEvent<Paquete> e) {
		this.paqueteSelected = e.getObject();
		this.messageConfirmDialog = this.paqueteSelected.getIdString();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectPaquete(UnselectEvent<Paquete> e) {
		this.paqueteSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editPaquete() {
		if (this.paqueteSelected != null) {
			this.paquete = this.paqueteSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelPaquete() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deletePaquete() {
		if (this.paqueteSelected != null) {
			try {
				paqueteService.deleteById(this.paqueteSelected.getId());
				cleanForm();
				loadPaquetes();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByDescripcion() {
		if (!this.paqueteSearch.getDescripcion().isEmpty()) {
			try {
				this.paquetes = new ArrayList<>();
				Optional<Paquete> optional = paqueteService.findByDescripcion(paqueteSelected.getDescripcion());
				if (optional.isPresent()) {
					this.paquetes.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyDescripcion() {
		this.paqueteSearch.setDescripcion("");
		loadPaquetes();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelPaquete = "display:none;";
		this.styleTablePaquete = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelPaquete = "display:block;";
		this.styleTablePaquete = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelPaquete = "display:none;";
		this.styleTablePaquete = "display:block;";
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

	public List<Paquete> getPaquetes() {
		return paquetes;
	}

	public Paquete getPaquete() {
		return paquete;
	}

	public Paquete getPaqueteSelected() {
		return paqueteSelected;
	}

	public List<Envio> getEnvios() {
		return envios;
	}

	public PaqueteService getPaqueteService() {
		return paqueteService;
	}

	public EnvioService getEnvioService() {
		return envioService;
	}

	public String getStylePanelPaquete() {
		return stylePanelPaquete;
	}

	public Paquete getPaqueteSearch() {
		return paqueteSearch;
	}

	public String getStyleTablePaquete() {
		return styleTablePaquete;
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
