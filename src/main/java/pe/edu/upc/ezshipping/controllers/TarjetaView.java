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

import pe.edu.upc.ezshipping.models.entities.Tarjeta;
import pe.edu.upc.ezshipping.services.TarjetaService;
import pe.edu.upc.ezshipping.utils.Action;

@Named("tarjetaView") // para las tablas de BD en la interface
@ViewScoped
public class TarjetaView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Tarjeta> tarjetas;
	// objeto asociado al formulario
	private Tarjeta tarjeta;
	// objeto asociado al rowSelect del datatable
	private Tarjeta tarjetaSelected;
	// objeto asociado al search
	private Tarjeta tarjetaSearch;

	private Action action;
	// Style for Panel and Data
	private String stylePanelTarjeta;
	private String styleTableTarjeta;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGuardar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	@Inject
	private TarjetaService tarjetaService;

	@PostConstruct
	private void init() {
		this.tarjetaSearch = new Tarjeta();
		this.cleanForm();
		this.loadTarjetas();
		this.action = Action.NONE;
		this.stateList();
	}

	public void loadTarjetas() {
		try {
			this.tarjetas = tarjetaService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
	}

	public void cleanForm() {
		this.tarjeta = new Tarjeta();
		this.tarjetaSelected = null;
	}

	// metodo cuando se hace clic en nuevo
	public void newTarjeta() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
		this.addMessage("Hice click en Nuevo");
	}

	// metodo para grabar
	public void saveTarjeta() {
		boolean uniqueNumero = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				// Para verificar que si el nombre es único
				Optional<Tarjeta> optional = tarjetaService.findByNumero(tarjetaSearch.getNroTarjeta());
				if (optional.isPresent()) {
					if (!optional.get().getId().equals(tarjeta.getId())) {
						uniqueNumero = false;
					}
				}
				if (uniqueNumero == true) {
					if (this.action == Action.NEW)
						tarjetaService.save(this.tarjeta);
					else
						tarjetaService.update(this.tarjeta);
					cleanForm();
					loadTarjetas();
					this.action = Action.NONE;
					this.stateList();
					this.addMessage("Guardado correctamente");
				} else {
					this.addMessage("La tarjeta: " + tarjeta.getNroTarjeta() + " ya se encuentra registrada");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e.getMessage());
			}
		}
	}

	// metodo que se ejecuta cuando el evento rowSelect ocurra:
	public void selectTarjeta(SelectEvent<Tarjeta> e) {
		this.tarjetaSelected = e.getObject();
		this.messageConfirmDialog = this.tarjetaSelected.getNroTarjeta();
		this.stateSelectRow();
	}

	// metodo que se ejecuta cuando el evento rowUnselect ocurra:
	public void unselectTarjeta(UnselectEvent<Tarjeta> e) {
		this.tarjetaSelected = null;
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Editar
	public void editTarjeta() {
		if (this.tarjetaSelected != null) {
			this.tarjeta = this.tarjetaSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelTarjeta() {
		cleanForm();
		this.stateList();
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteTarjeta() {
		if (this.tarjetaSelected != null) {
			try {
				tarjetaService.deleteById(this.tarjetaSelected.getId());
				cleanForm();
				loadTarjetas();
				this.action = Action.NONE;
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void findByNumero() {
		if (!this.tarjetaSearch.getNroTarjeta().isEmpty()) {
			try {
				this.tarjetas = new ArrayList<>();
				Optional<Tarjeta> optional = tarjetaService.findByNumero(tarjetaSearch.getNroTarjeta());
				if (optional.isPresent()) {
					this.tarjetas.add(optional.get());
				}
				this.stateList();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void cleanbyNumero() {
		this.tarjetaSearch.setNroTarjeta("");
		loadTarjetas();
		this.stateList();
	}

	// State on Application
	// los que están en false, se muestran en pantalla
	public void stateList() {
		this.stylePanelTarjeta = "display:none;";
		this.styleTableTarjeta = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGuardar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelTarjeta = "display:block;";
		this.styleTableTarjeta = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGuardar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelTarjeta = "display:none;";
		this.styleTableTarjeta = "display:block;";
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

	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public Tarjeta getTarjetaSelected() {
		return tarjetaSelected;
	}

	public TarjetaService getTarjetaService() {
		return tarjetaService;
	}

	public String getStylePanelTarjeta() {
		return stylePanelTarjeta;
	}

	public Tarjeta getTarjetaSearch() {
		return tarjetaSearch;
	}

	public String getStyleTableTarjeta() {
		return styleTableTarjeta;
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
