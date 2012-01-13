package lector.client.reader;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.Finder2;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.login.ActualUser;

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

public class SelectorNewBetweenTypeAndFolder extends PopupPanel {

	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private ListBox comboBox;
	private Finder2 finder;
	private TextBox textBox;

	public SelectorNewBetweenTypeAndFolder(Finder2 finderin) {
		super(true);
		this.finder=finderin;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("237px", "170px");
		
		Label lblNewLabel = new Label("Select Between Tag Or Folder an type the new object");
		verticalPanel.add(lblNewLabel);
		
		comboBox = new ListBox();
		comboBox.addItem("Type");
		comboBox.addItem("Folder");
		verticalPanel.add(comboBox);
		comboBox.setWidth("98px");
		
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
				String Seleccion=comboBox.getItemText(comboBox.getSelectedIndex());

				
				AsyncCallback<Long> callback = new AsyncCallback<Long>() {

					public void onFailure(Throwable caught) {
						
						hide();
						

				        
						
					}

					

					public void onSuccess(Long result) {
						finder.RefrescaLosDatos();
						hide();
					}
				};
				
				if (Seleccion.equals("Type")){
				File F=new File(textBox.getText(),null, finder.getCatalogo().getId());
				F.setFather(finder.getTopPath());
				bookReaderServiceHolder.saveFile(F, callback);
				}
				else {
					Folder F=new Folder(textBox.getText(),null,finder.getCatalogo().getId());
					F.setFather(finder.getTopPath());
					bookReaderServiceHolder.saveFolder(F, callback);
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
		horizontalPanel.add(btnNewButton_1);
	}

}
