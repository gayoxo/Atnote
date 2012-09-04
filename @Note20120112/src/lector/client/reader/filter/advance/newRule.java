package lector.client.reader.filter.advance;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class newRule extends PopupPanel{
	
	private TextBox textBox;

	public newRule() {
		super(false);
		setGlassEnabled(true);
		setModal(true);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		Label InformatiolRule = new Label("Insert Name For Rule:");
		verticalPanel.add(InformatiolRule);
		InformatiolRule.setWidth("100%");
		
		textBox = new TextBox();
		textBox.setVisibleLength(40);
		verticalPanel.add(textBox);
		textBox.setWidth("97%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(4);
		verticalPanel.add(horizontalPanel);
		
		Button Ok = new Button("Ok");
		Ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBox.getText().length()>=0){
					FilterAdvance.getRules().add(new Rule(textBox.getText()));
					hide();
				}
				else Window.alert("Error el tamaño ha de ser mayor que 0");
			}
		});
		Ok.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
		Ok.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		Ok.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		Ok.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		Ok.setStyleName("gwt-ButtonCenter");
		
		horizontalPanel.add(Ok);
		
		Button Cancel = new Button("Cancel");
		Cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		Cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBox.getText().length()>=0){
				//	FilterAdvance.getRules().add(new Rule(textBox.getText()));
					hide();
				}
				else Window.alert("Error el tamaño ha de ser mayor que 0");
			}
		});
		Cancel.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
		Cancel.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		Cancel.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		Cancel.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		Cancel.setStyleName("gwt-ButtonCenter");
		horizontalPanel.add(Cancel);
		
	}

}
