package lector.client.admin.langedit;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class newLang extends PopupPanel {

	private PopupPanel Me;
	private NewAdminLangs Father;
	private TextBox textBox;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public newLang(NewAdminLangs Fatherin) {
		super(true);
		this.Father=Fatherin;
		Me=this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(3);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		Label label = new Label("Insert the name for the new Language");
		verticalPanel.add(label);
		label.setSize("100%", "100%");
		
		textBox = new TextBox();
		verticalPanel.add(textBox);
		textBox.setWidth("98%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(4);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		Button btnNewButton = new Button("Create");
		btnNewButton.setSize("100%", "100%");
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
				
				
					
					
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Saving...");
					bookReaderServiceHolder.getLanguagesNames(new AsyncCallback<ArrayList<String>>() {
						
						public void onSuccess(ArrayList<String> result) {
							LoadingPanel.getInstance().hide();
							String S=textBox.getText();
							Language L=new Language(S);
							if (S.isEmpty()|| S.length()<2) 
								Window.alert("The Name can be more lenght or equal than two");
							else {
							ArrayList<String> LL =result;
							boolean encontrado=false;
							for (String LLL : LL) {
								encontrado=LLL.equals(S);
								if (encontrado) break;
							}
							
							
							if (!encontrado) 
								{
								LoadingPanel.getInstance().center();
								LoadingPanel.getInstance().setLabelTexto("Saving...");
								bookReaderServiceHolder.saveLanguage(L, new AsyncCallback<Void>(){
								

								public void onFailure(Throwable caught) {
									LoadingPanel.getInstance().hide();
									Window.alert("I can save the Lenguaje");
									
								}

								public void onSuccess(Void result) {
									LoadingPanel.getInstance().hide();
									Father.refresh();
									Me.hide();
									
								}
								
							});
								}
							else{ Window.alert("This Language exist previusly");
							LoadingPanel.getInstance().hide();
							}
							}
							
						}
						
						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert("I can't load the Lenguajes");
							
						}
					});
					
					
				}
				
			
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Cancel");
		btnNewButton_1.setSize("100%", "100%");
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
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Me.hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
		super.setGlassEnabled(true);
	}

}
