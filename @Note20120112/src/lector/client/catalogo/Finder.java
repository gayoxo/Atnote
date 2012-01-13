package lector.client.catalogo;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.FileException;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickEvent;

public class Finder extends Composite {

	private ArrayList<Entity> Path;
	private Catalog C;
	private StackPanelMio SPmio;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private HorizontalPanel horizontalPanel;
	private ArrayList<Entity> AMostrar;
	private Button button;
	
	//el finder del reading activity tiene lenguaje asociado
	private boolean InReadingActivity=false;

	public Finder() {

		Path = new ArrayList<Entity>();
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);

		button = new Button("<");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!Path.isEmpty()) {
					Entity Rem = Path.get(Path.size() - 1);
					Path.remove(Rem);
					RefrescaLosDatos();
					if (Path.isEmpty())
						button.setVisible(false);
				}
			}
		});
		horizontalPanel.add(button);
		button.setVisible(false);

		for (int j = 0; ((j < Path.size()) && j < 3); j++) {
			int i;
			if (Path.size() <= 3)
				i = j;
			else
				i = Path.size() - 3 + j;
			Button buttonnew = new Button(Path.get(i).getName());
			horizontalPanel.add(buttonnew);
		}

		SPmio = new StackPanelMio();
		verticalPanel.add(SPmio);
		SPmio.setWidth("100%");

	}

	public void RefrescaLosDatos() {
		AsyncCallback<ArrayList<Entity>> callback1 = new AsyncCallback<ArrayList<Entity>>() {

			public void onFailure(Throwable caught) {
				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
				else Window.alert("Error : I can't refresh the types");
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Entity> result) {
				try {

					if (Path.isEmpty()) {
						C.setSons(result);
					} else {
						Path.get(Path.size() - 1).setSons(result);
						button.setVisible(true);
					}

				} catch (FileException e1) {
					e1.printStackTrace();
				}

				horizontalPanel.clear();
				horizontalPanel.add(button);

				for (int j = 0; ((j < Path.size()) && j < 3); j++) {
					int i;
					if (Path.size() <= 3)
						i = j;
					else
						i = Path.size() - 3 + j;
					ButtonNavigator buttonnew = new ButtonNavigator(Path.get(i)
							.getName());
					buttonnew.setElemento(Path.get(i));
					buttonnew.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							ButtonNavigator BN = (ButtonNavigator) event
									.getSource();
							int start = Path.size() - 1;
							while (!Path.isEmpty() && start >= 0
									&& Path.get(start) != BN.getElemento()) {
								Path.remove(start);
								start--;
							}
							RefrescaLosDatos();

						}
					});
					horizontalPanel.add(buttonnew);
				}

				AMostrar = result;

				SPmio.Clear();
				for (Entity entitynew : AMostrar) {
					if (AMostrar.size() <= 10)
						SPmio.addBotonLessTen(entitynew);
					else
						SPmio.addBoton(entitynew);
				}
				SPmio.ClearEmpty();
				LoadingPanel.getInstance().hide();
			}
		};
		LoadingPanel.getInstance().center();
		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getLoading());
		else LoadingPanel.getInstance().setLabelTexto("Loading...");
		Long IdPathActual = 0l;
		if (Path.isEmpty())
			IdPathActual = null;
		else
			IdPathActual = Path.get(Path.size() - 1).getID();
		bookReaderServiceHolder.getSons(IdPathActual, C
				.getId(), callback1);
	}

	public void setButtonClick(ClickHandler botonClick, String NameEntity) {
		SPmio.setBotonClick(botonClick);
	}

	public void add2Pad(Entity E) {
		Path.add(E);
		button.setVisible(true);
	}

//	private void remove2Pad(Entity E) {
//		Path.remove(E);
//		if (Path.isEmpty())
//			button.setVisible(false);
//	}

	public ArrayList<Entity> getSonPathActual() {
		if (Path.isEmpty())
			return C.getSons();
		else
			try {
				return Path.get(Path.size() - 1).getSons();
			} catch (FileException e) {
				
					Window.alert("Server Error : I try to get sons from a file refresh page");
			}
		return new ArrayList<Entity>();
	}

	public void setBotonClick(ClickHandler clickHandler) {
		SPmio.setBotonClick(clickHandler);

	}

	public Entity getSonbyName(String html) {
		ArrayList<Entity> PathActual = getSonPathActual();
		boolean found = false;
		int counter = 0;
		while (!found && counter < PathActual.size()) {
			found = PathActual.get(counter).getName().equals(html);
			counter++;
		}
		if (found)
			return PathActual.get(counter - 1);
		else
			return null;
	}

	public void setButtonTipo(BotonesStackPanelMio buttonMio) {
		SPmio.setBotonTipo(buttonMio);

	}

	public Entity getTopPath() {
		if (Path.isEmpty())
			return null;
		return Path.get(Path.size() - 1);
	}
	
	public Catalog getCatalogo() {
		return C;
	}
	
	public void setCatalogo(Catalog c) {
		C = c;
	}
	
	public void setInReadingActivity(boolean inReadingActivity) {
		InReadingActivity = inReadingActivity;
	}
}
