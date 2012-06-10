package lector.client.catalogo;

import java.util.ArrayList;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

public class ElementKey extends Composite{
	
	private Entity Entidad;
	private String Text;
	private ButtonKey Mas;
	private ButtonKey Label;
	private VerticalPanelEspacial NextBotones;
	private enum State {Open,Close};
	private State Actual;
	private Image Large;
	private Image Compact;
	private Button NoSOns;
	private VerticalPanel verticalPanel;
	private Label Others;
	private boolean Selected;
	private ButtonKey Actulizador;
	private ArrayList<ElementKey> Otros;
	private String StlieOld;
	
	public ElementKey(Entity ent) {
		
		Entidad=ent;
		Selected=false;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(horizontalPanel);
//		horizontalPanel.setSize("", "");
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel);
		
		Otros = new ArrayList<ElementKey>();
		Otros.add(this);
		
		HorizontalPanel BotonT = new HorizontalPanel();
		verticalPanel.add(BotonT);
		BotonT.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		BotonT.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BotonT.setSize("100%", "100%");
		
		Label = new ButtonKey("New button",this);
		//Label = new ButtonKey("New button",null);
		BotonT.add(Label);
		Label.setSize("100%", "43px");
		Label.setStyleName("gwt-ButtonIzquierdaMIN");
		Label.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!Selected)
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaMIN");
			}
		});
		Label.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				if (!Selected)
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOverMIN");
			}
		});
		Label.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPushMIN");
			}
		});
		Label.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaMIN");
			}
		});
        
		Mas = new ButtonKey("-",this);
		//Mas = new ButtonKey("+",null);
		BotonT.add(Mas);
		Mas.setSize("48px", "43px");
		Mas.setStyleName("gwt-ButtonDerechaMIN");
Mas.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if (Actual==State.Close)
				{
				Large.setVisible(true);
				NextBotones.setVisible(true);
				Mas.setText("-");
				Actual=State.Open;
				Compact.setVisible(false);			
				}else 
				{
					Large.setVisible(false);
					NextBotones.setVisible(false);
					Mas.setText("+");
					Actual=State.Close;
					Compact.setVisible(true);	
				}
				if (Entidad instanceof File)
					isAFile();
				
			}
		});
		
		
		Mas.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaMIN");
			}
		});
		Mas.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOverMIN");
			}
		});
		Mas.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPushMIN");
			}
		});
		Mas.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaMIN");
			}
		});
		
		
		NoSOns = new Button(" ");
		BotonT.add(NoSOns);
		NoSOns.setSize("48px", "43px");
		NoSOns.setStyleName("gwt-ButtonDerecha");
		
		Others = new Label("Press to show others");
		Others.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (StlieOld!=null&&!StlieOld.isEmpty())
				{
				Label.setStyleName(StlieOld);

				for (ElementKey It : Otros) {
					It.setStyleNameLabelOld();
				}
					}
			}
		});
		Others.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				if (StlieOld!=null&&!StlieOld.isEmpty())
					{
					Label.setStyleName(StlieOld);

					for (ElementKey It : Otros) {
						It.setStyleNameLabelOld();
					}
						}
			}
		});
		Others.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				StlieOld=Label.getStyleName();
			//	Label.setStyleName("gwt-ButtonIzquierdaSelectMIN");
				for (ElementKey It : Otros) {
					It.setStyleNameLabelNew();
				}
			}
		});
		Others.setVisible(false);
		Others.setStyleName("gwt-LabelShowMore");
		
		verticalPanel.add(Others);
		
		Actulizador = new ButtonKey("Actualizacion",this);;

		verticalPanel.add(Actulizador);
		Actulizador.setVisible(false);
		
		NoSOns.setVisible(false);
		
		Large = new Image("ArbolLine.jpg");
		horizontalPanel.add(Large);
				Large.setVisible(true);
				Large.setSize("35px", "100%");
		
		Compact = new Image("ArbolLineChico.jpg");
		horizontalPanel.add(Compact);
		Compact.setVisible(false);
		Actual=State.Open;
		
		NextBotones = new VerticalPanelEspacial(this);
		NextBotones.setVisible(true);
		horizontalPanel.add(NextBotones);
		NextBotones.setSize("100%", "");
		
	}

	protected void setStyleNameLabelOld() {
		Label.setStyleName(StlieOld);
		
	}

	protected void setStyleNameLabelNew() {
		StlieOld=Label.getStyleName();
		Label.setStyleName("gwt-ButtonIzquierdaSelectMIN");
		LLamada_En_Cadena();
		
	}

	private void LLamada_En_Cadena() {
		setOpen();
		Widget VPContenedorW =getParent();
		if (VPContenedorW instanceof VerticalPanelEspacial)
		{
			VerticalPanelEspacial VPContenedor =(VerticalPanelEspacial)VPContenedorW;
			VPContenedor.getEK().setOpen();
			VPContenedor.getEK().LLamada_En_Cadena();
		
		}
		
	}

	private void setOpen() {
		Large.setVisible(true);
		NextBotones.setVisible(true);
		Mas.setText("-");
		Actual=State.Open;
		Compact.setVisible(false);	
		if (Entidad instanceof File)
			isAFile();
		
		
	}

	public void addClickButtonMas(ClickHandler Clic) {
		Actulizador.addClickHandler(Clic);
		
		
		
	}
	
	public void addClickButton(ClickHandler Clic) {
		Label.addClickHandler(Clic);
		
	}

	public Entity getEntidad() {
		return Entidad;
	}

	public void removeItems() {
		NextBotones.clear();
		
	}
	
	public void setHTML(String S,String Text) {
		this.Text=Text;
		Label.setHTML("<img src=\""+ S +"\">"+ Text);
	}

	public void addItem(ElementKey a) {
		NextBotones.add(a);
		
	}

	public void setText(String catalogName) {
		Label.setHTML(catalogName);
		
	}

	public void isAFile() {
		Mas.setVisible(false);
		NoSOns.setVisible(true);
		Compact.setVisible(false);
		Large.setVisible(false);
		NextBotones.setVisible(false);
		
	}
	
	public ButtonKey getMas() {
		return Mas;
	}
	
	public ButtonKey getLabel() {
		return Label;
	}
	
	public boolean isSelected() {
		return Selected;
	}
	
	public void setSelected(boolean selected) {
		Selected = selected;
	}
	
	public ButtonKey getActulizador() {
		return Actulizador;
	}
	
	public Label getOthers() {
		return Others;
	}

//	public void setlabelStyle(String string) {
//		Label.setStyleName(string);
//		for (ElementKey It : Otros) {
//			It.getLabel().setStyleName(string);
//		}
//		
//	}
	public ArrayList<ElementKey> getOtros() {
		return Otros;
	}
	
	public void setOtros(ArrayList<ElementKey> otros) {
		Otros = otros;
	}
}
