package lector.client.reader;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.FileException;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.client.FolderException;
import lector.client.login.ActualUser;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class SelectorNewBetweenTypeAndFolder extends PopupPanel {

	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private ListBox comboBox;
	private Finder finderrefresh;
	private Finder finder;
	private TextBox textBox;

	public SelectorNewBetweenTypeAndFolder(Finder finderin,Finder finderrefreshin) {
		super(true);
		setGlassEnabled(true);
		this.finderrefresh=finderrefreshin;
		finder=finderin;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("237px", "170px");
		
		Label lblNewLabel = new Label("Select Between \"Type Category\" or  \"Type\" to create the new object");
		verticalPanel.add(lblNewLabel);
		
		comboBox = new ListBox();
		comboBox.addItem("Type Category");
		comboBox.addItem("Type");

		verticalPanel.add(comboBox);
		comboBox.setWidth("145px");
		
		textBox = new TextBox();
		verticalPanel.add(textBox);
		textBox.setWidth("238px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(10);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		
		Button btnNewButton = new Button("Create");
btnNewButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		btnNewButton.setStyleName("gwt-ButtonCenter");
		
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String Seleccion=comboBox.getItemText(comboBox.getSelectedIndex());

				
				AsyncCallback<Long> callback = new AsyncCallback<Long>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						//TODO Modificar lenguaje etiquetas para los errores.
						if (caught instanceof FileException) {
							Window.alert(((FileException) caught)   // Se ataja cuando se guarda un file con un nombre que ya existe.
									.getMessage());
						} else if (caught instanceof FolderException) {
							Window.alert(((FolderException) caught)   // Se ataja cuando se guarda un folder con un nombre que ya existe en su mismo nivel.
									.getMessage());
						} else {
							Window.alert("The file could not be saved");
						}
						Window.alert(ActualUser.getLanguage().getE_Saving());
						hide();
						

				        
						
					}

					

					public void onSuccess(Long result) {
						finderrefresh.RefrescaLosDatos();
						SelectorTypePopUpAnnotacion.RestoreFinderButtonActio();;
						LoadingPanel.getInstance().hide();
						hide();
					}
				};
				
				LoadingPanel.getInstance().setLabelTexto("Saving...");
				LoadingPanel.getInstance().center();
				
				if (Seleccion.equals("Type")){
				File F=new File(textBox.getText(),null, finder.getCatalogo().getId());
		
				bookReaderServiceHolder.saveFile(F,finder.getTopPath().getID(), callback);
				}
				else {
					Folder F=new Folder(textBox.getText(),null,finder.getCatalogo().getId());
					bookReaderServiceHolder.saveFolder(F,finder.getTopPath().getID(), callback);
				}
				hide();
				
			}
		});
		
		
		
		
		
		
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Cancel");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnNewButton_1.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		btnNewButton_1.setStyleName("gwt-ButtonCenter");
		horizontalPanel.add(btnNewButton_1);
	}

}
