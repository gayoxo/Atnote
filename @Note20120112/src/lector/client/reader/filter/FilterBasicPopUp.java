package lector.client.reader.filter;

import java.util.ArrayList;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.MainEntryPoint;
import lector.share.model.Language;
import lector.share.model.UserApp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;

public class FilterBasicPopUp extends PopupPanel {
	private HorizontalPanel horizontalPanel;
	private Button All; 
	private static Boolean AllBoolean;
	private PopupPanel Me=this;
	private Language Lang;
	private static TextBox textBox;
	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private ArrayList<Long> filtro = new ArrayList<Long>();
	private Button Advance;
	private ArrayList<String> Types;
	private ArrayList<UserApp> GroupUser;
	
	public FilterBasicPopUp() {
		super(true);
		setAnimationEnabled(true);
		AllBoolean=false;
		filtro = new ArrayList<Long>();
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		SimplePanel simplePanel = new SimplePanel();
		verticalPanel.add(simplePanel);
		simplePanel.setSize("100%", "100%");
		
		if (textBox==null) textBox = new TextBox();
		textBox.setVisibleLength(100);
		simplePanel.setWidget(textBox);
		textBox.setSize("98%", "100%");
		Lang=ActualUser.getLanguage();
	
	horizontalPanel = new HorizontalPanel();
	verticalPanel.add(horizontalPanel);
	
	Button btnNewButton = new Button(Lang.getFilterButton());
	horizontalPanel.add(btnNewButton);
	horizontalPanel.setCellHorizontalAlignment(btnNewButton, HasHorizontalAlignment.ALIGN_CENTER);
	
	All = new Button(Lang.getSelect_All());
	All.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			AllBoolean=!AllBoolean;
			if (AllBoolean){
				textBox.setEnabled(false);
				textBox.setReadOnly(true);
				textBox.setText(Lang.getAllSelected());
				
			}
			else 
			{
				textBox.setEnabled(true);
				textBox.setReadOnly(false);
				textBox.setText("");
			}
		}
	});
	horizontalPanel.add(All);
	All.setSize("100%", "100%");
	All.setStyleName("gwt-ButtonCenter");
	All.addMouseOutHandler(new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
	All.addMouseOverHandler(new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
		}
	});
	All.addMouseDownHandler(new MouseDownHandler() {
		public void onMouseDown(MouseDownEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
		}
	});
	
	Advance = new Button(Lang.getAdvance());
	Advance.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			Controlador.change2FilterAdvance();
		}
	});
	Advance.setSize("100%", "100%");
	Advance.setStyleName("gwt-ButtonCenter");
	Advance.addMouseOutHandler(new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
	Advance.addMouseOverHandler(new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
		}
	});
	Advance.addMouseDownHandler(new MouseDownHandler() {
		public void onMouseDown(MouseDownEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
		}
	});
	horizontalPanel.add(Advance);

	btnNewButton.setSize("100%", "100%");
		btnNewButton.setStyleName("gwt-ButtonCenter");
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
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		btnNewButton.addClickHandler(new ClickHandler() {


			public void onClick(ClickEvent event) {
				filtro=new ArrayList<Long>();
				if (!AllBoolean){
				String Token=textBox.getText();
				String[] Words= Token.split(",");
				if (Words.length>0){
					Types=MakeWords(Words);
					bookReaderServiceHolder.getEntriesIdsByNames(Types, ActualUser.getCatalogo().getId(), ActualUser.getOpenCatalog().getId(),new AsyncCallback<ArrayList<Long>>() {

						public void onFailure(Throwable caught) {
							Window.alert(ActualUser.getLanguage().getE_typeFilter());
							Me.hide();
						}

						public void onSuccess(ArrayList<Long> result) {
							filtro.addAll(result);
							//MainEntryPoint.setFiltroTypes(filtro);
							FiltroPorUsers();
							Me.hide();
						}

						private void FiltroPorUsers() {
							bookReaderServiceHolder.getUsersByGroupId(ActualUser.getReadingactivity().getGroupId(),new AsyncCallback<ArrayList<UserApp>>() {
								
								public void onSuccess(ArrayList<UserApp> result) {
									
									GroupUser=result;
									
									bookReaderServiceHolder.loadUserById(ActualUser.getReadingactivity().getProfessorId(), new AsyncCallback<UserApp>() {
										
										public void onSuccess(UserApp result) {
											GroupUser.add(result);
											
											ArrayList<Long> UserIds=new ArrayList<Long>();

											for (UserApp user : GroupUser) {
												if (IsSimilar(user)) UserIds.add(user.getId());
											}
											
											MainEntryPoint.setFiltroTypesAndUser(filtro, UserIds);
											
										}
										
										public void onFailure(Throwable caught) {
											Window.alert(ActualUser.getLanguage().getE_UserLoad());
											Me.hide();
											
										}
									});
									
								
									
									
									
								}
								
								private boolean IsSimilar(UserApp user) {
									for (String uni : Types) {
										if (user.getEmail().contains(uni))
											return true;
									}
									return false;
								}

								public void onFailure(Throwable caught) {
									Window.alert(ActualUser.getLanguage().getE_UserLoad());
									Me.hide();
									
								}
							});
							
						}
					});
					
					/*StringBuffer SB=new StringBuffer();
					for (int i = 0; i < Types.size(); i++) {
						if (i!=0) SB.append(" - "); 
						SB.append(i+" : " + Types.get(i));
					}
					Window.alert(SB.toString());*/
				}
				}else {
					filtro.add(Long.MIN_VALUE);
				MainEntryPoint.setFiltroTypes(filtro);
				Me.hide();
				}
			
		}

			private ArrayList<String> MakeWords(String[] words) {
				ArrayList<String> Salida=new ArrayList<String>();
				for (String SS : words) {
					if (!SS.isEmpty()) Salida.add(SS);
				} 
				return Salida;
			}


			
				
		});
	
		}
	
}
