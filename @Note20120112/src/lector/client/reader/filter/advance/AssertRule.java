package lector.client.reader.filter.advance;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class AssertRule extends Composite {
	private ButtonFilter AssertName;
	private VerticalPanel Parental;
	private boolean stateImage;
	private Button btnNewButton;

	public AssertRule(String Name,VerticalPanel Parentin,Long Id, Tiposids TI) {
		setStateImage(false);
		Parental=Parentin;
		FlowPanel horizontalPanel = new FlowPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("80%", "100%");
		
		AssertName = new ButtonFilter(Name,Id,TI);
		AssertName.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ButtonFilter Yo=(ButtonFilter)event.getSource();
				AssertRule YoA=findeme(Yo);
				Parental.remove(YoA);
			}

			private AssertRule findeme(ButtonFilter yo) {
				for (int i = 0; i < Parental.getWidgetCount(); i++) {
					if (yo==((AssertRule)Parental.getWidget(i)).getAssertName()) return (AssertRule) Parental.getWidget(i);
				}
				
				return null;
			}
		});
		
		
		AssertName.setStyleName("gwt-ButtonIzquierda");
		AssertName.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
		AssertName.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
			}
		});
		AssertName.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
		
	
		AssertName.setSize("80%", "");
		setStateImage(true);
		horizontalPanel.add(AssertName);
		
				
		
		btnNewButton = new Button("New button");
		btnNewButton.setHTML("+");
		//btnNewButton.setHTML("+");
		horizontalPanel.add(btnNewButton);
		btnNewButton.setSize("20%", "");
		btnNewButton.setStyleName("gwt-ButtonDerecha");
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setStateImage(!isStateImage());
				if (stateImage) 
					btnNewButton.setHTML("+");
					else btnNewButton.setHTML("-");
				
			}
		});
		
	}

	public void setAssertName(ButtonFilter assertName) {
		AssertName = assertName;
	}
	public ButtonFilter getAssertName() {
		return AssertName;
	}
	public void setParental(VerticalPanel parent) {
		Parental = parent;
	}
	public VerticalPanel getParental() {
		return Parental;
	}

	public boolean isStateImage() {
		return stateImage;
	}

	public void setStateImage(boolean stateImage) {
		this.stateImage = stateImage;
	}
	
	
}
