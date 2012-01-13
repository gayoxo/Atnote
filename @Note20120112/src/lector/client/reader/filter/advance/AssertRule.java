package lector.client.reader.filter.advance;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;

public class AssertRule extends Composite {
	private ButtonFilter AssertName;
	private VerticalPanel Parental;
	private Image image;
	private boolean stateImage;
	private final String Plus="Plus.gif";
	private final String Less="Less.gif";

	public AssertRule(String Name,VerticalPanel Parentin,Long Id, Tiposids TI) {
		
		setStateImage(false);
		Parental=Parentin;
		FlowPanel horizontalPanel = new FlowPanel();
		horizontalPanel.setStyleName("Root");
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
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
		
		image = new Image(Plus);
//		image.addMouseDownHandler(new MouseDownHandler() {
//			public void onMouseDown(MouseDownEvent event) {
//				setStateImage(!isStateImage());
//				if (stateImage) image.setUrl(Plus);
//					else image.setUrl(Less);
//			}
//		});
		setStateImage(true);
		image.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setStateImage(!isStateImage());
				if (stateImage) image.setUrl(Plus);
					else image.setUrl(Less);
				
			}
		});
		image.setStyleName("image2");
		horizontalPanel.add(image);
		image.setSize("3%", "24px");
		horizontalPanel.add(AssertName);
		AssertName.setSize("97%", "100%");
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
