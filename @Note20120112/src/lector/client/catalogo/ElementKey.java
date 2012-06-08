package lector.client.catalogo;

import lector.client.catalogo.client.Entity;
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

public class ElementKey extends Composite{
	
	private Entity Entidad;
	private String Text;
	private ButtonKey Mas;
	private ButtonKey Label;
	private VerticalPanel NextBotones;
	private enum State {Open,Close};
	private State Actual;
	private Image Large;
	private Image Compact;
	private Button NoSOns;
	
	public ElementKey(Entity ent) {
		
		Entidad=ent;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(horizontalPanel);
		horizontalPanel.setWidth("");
		
		HorizontalPanel BotonT = new HorizontalPanel();
		BotonT.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		BotonT.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(BotonT);
		BotonT.setSize("", "100%");
		
		Label = new ButtonKey("New button",this);
		//Label = new ButtonKey("New button",null);
		BotonT.add(Label);
		Label.setSize("100%", "43px");
		Label.setStyleName("gwt-ButtonIzquierda");
		Label.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
		Label.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
			}
		});
		Label.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
		Label.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
        
		Mas = new ButtonKey("+",this);
		//Mas = new ButtonKey("+",null);
		BotonT.add(Mas);
		Mas.setSize("48px", "43px");
		Mas.setStyleName("gwt-ButtonDerecha");
		
		
		Mas.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		Mas.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
		Mas.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
		Mas.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		
		
		NoSOns = new Button(" ");
		BotonT.add(NoSOns);
		NoSOns.setSize("48px", "43px");
		NoSOns.setStyleName("gwt-ButtonDerecha");
		
		NoSOns.setVisible(false);
		
		Large = new Image("ArbolLine.jpg");
		horizontalPanel.add(Large);
		Large.setVisible(false);
		Actual=State.Close;
		Large.setSize("35px", "100%");
		
		Compact = new Image("ArbolLineChico.jpg");
		horizontalPanel.add(Compact);
		
		NextBotones = new VerticalPanel();
		NextBotones.setVisible(false);
		horizontalPanel.add(NextBotones);
		NextBotones.setSize("100%", "100%");
		
	}

	public void addClickButtonMas(ClickHandler Clic) {
		Mas.addClickHandler(Clic);
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
				
			}
		});
		
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
		
	}
}
