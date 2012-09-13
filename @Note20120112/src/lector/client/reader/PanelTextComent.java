package lector.client.reader;

import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.share.model.Annotation;
import lector.share.model.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;

public class PanelTextComent extends Composite {

	private RichTextArea richTextArea;
	private RichTextToolbar toolbar;
	private HorizontalPanel horizontalPanel;
	private HorizontalPanel horizontalPanel_1;
	private Language ActualLang;
	private SelectorTypePopUpAnnotacion finder;
	private Button BotonSelectType;
	private HorizontalPanel horizontalPanel_3;
	private HorizontalPanel horizontalPanel_4;
	private Label LabelPrivPub;
	private ListBox comboBox;
	private CheckBox chckbxNewCheckBox;
	private HorizontalPanel horizontalPanel_2;
	private Button BotonSelectTypePublic;
	private HorizontalPanel PenelBotonesTipo;
	private ScrollPanel scrollPanel;

	public enum CatalogTipo {
		
		Catalog1("<img src=\"File.gif\">"), Catalog2("<img src=\"File2.gif\">");
		
		private String Texto;
		
		private CatalogTipo(String A) {
			Texto=A;
		}
		
		public String getTexto() {
			return Texto;
		}
		
	};
	public PanelTextComent(Language ActualLangin) {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		ActualLang=ActualLangin;
		richTextArea = new RichTextArea();
		toolbar = new RichTextToolbar(richTextArea);
		verticalPanel.add(toolbar);
		verticalPanel.add(richTextArea);
		richTextArea.setSize("100%", "100%");
		

		horizontalPanel = new HorizontalPanel();
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("");

		horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(6);
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setHeight("53px");

		BotonSelectType = new Button(ActualLang.getSetTypes());
		BotonSelectType.addClickHandler(new ClickHandler() {


			public void onClick(ClickEvent event) {
				finder = new SelectorTypePopUpAnnotacion(PenelBotonesTipo,ActualUser.getCatalogo(),CatalogTipo.Catalog1);
				finder.center();
				finder.setModal(true);
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		BotonSelectType.setSize("100%", "100%");
		BotonSelectType.setStyleName("gwt-ButtonCenter");
		BotonSelectType.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		BotonSelectType.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		BotonSelectType.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});

		horizontalPanel_1.add(BotonSelectType);
		
		horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setSpacing(6);
		horizontalPanel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(horizontalPanel_2);
		horizontalPanel_2.setHeight("53px");
		
		BotonSelectTypePublic = new Button(ActualLang.getSetTypesPublic());
		BotonSelectTypePublic.addClickHandler(new ClickHandler() {


			public void onClick(ClickEvent event) {
				finder = new SelectorTypePopUpAnnotacionPublic(PenelBotonesTipo,ActualUser.getOpenCatalog(),CatalogTipo.Catalog2);
				finder.center();
				finder.setModal(true);
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		
		BotonSelectTypePublic.setSize("100%", "100%");
		BotonSelectTypePublic.setStyleName("gwt-ButtonCenter");
		BotonSelectTypePublic.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		BotonSelectTypePublic.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		BotonSelectTypePublic.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		horizontalPanel_2.add(BotonSelectTypePublic);

		

		horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_3.setSpacing(6);
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(horizontalPanel_3);

		horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4.setSpacing(3);
		horizontalPanel_4
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_4
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_3.add(horizontalPanel_4);
		horizontalPanel_4.setHeight("42px");

		LabelPrivPub = new Label(ActualLang.getVisibility() + " : ");
		horizontalPanel_4.add(LabelPrivPub);

		comboBox = new ListBox();
		horizontalPanel_4.add(comboBox);
		comboBox.addChangeHandler(new ChangeHandler() {


			public void onChange(ChangeEvent event) {
				ListBox a = (ListBox) event.getSource();
				if (a.getItemText(a.getSelectedIndex()).equals(
						Constants.ANNOTATION_PUBLIC)) {
					chckbxNewCheckBox.setVisible(true);
				} else {
					chckbxNewCheckBox.setVisible(false);
				}
			}
		});
		
		comboBox.addItem(Constants.ANNOTATION_PUBLIC);
		comboBox.addItem(Constants.ANNOTATION_PRIVATE);
	

		chckbxNewCheckBox = new CheckBox(ActualLang.getUpgradeable());
		horizontalPanel_3.add(chckbxNewCheckBox);
		
		scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setWidth("100%");
		
		PenelBotonesTipo = new HorizontalPanel();
		scrollPanel.setWidget(PenelBotonesTipo);
		chckbxNewCheckBox.setVisible(true);

		// Previous = annotation.getTagsIds();
		// for (int i = 0; i < Previous.size(); i++) {
		// TagAddedButtonSelection newe = new TagAddedButtonSelection(
		// Previous.get(i), TagsAdadidos, ListaTags);
		// ListaTags.add(newe);
		// TagsAdadidos.add(newe);
		//
		// }

	}

	
	public void setRichTextArea(RichTextArea richTextArea) {
		this.richTextArea = richTextArea;
	}
	
	public RichTextArea getRichTextArea() {
		return richTextArea;
	}
	
	public HorizontalPanel getPenelBotonesTipo() {
		return PenelBotonesTipo;
	}
	
	public void setPenelBotonesTipo(HorizontalPanel penelBotonesTipo) {
		PenelBotonesTipo = penelBotonesTipo;
	}
	
	public CheckBox getChckbxNewCheckBox() {
		return chckbxNewCheckBox;
	}
	
	public void setChckbxNewCheckBox(CheckBox chckbxNewCheckBox) {
		this.chckbxNewCheckBox = chckbxNewCheckBox;
	}
	
	public void setComboBox(ListBox comboBox) {
		this.comboBox = comboBox;
	}
	
	public ListBox getComboBox() {
		return comboBox;
	}
	
	public RichTextToolbar getToolbar() {
		return toolbar;
	}
	
	public void setToolbar(RichTextToolbar toolbar) {
		this.toolbar = toolbar;
	}
	
	public Button getBotonSelectType() {
		return BotonSelectType;
	}
	
	public Button getBotonSelectTypePublic() {
		return BotonSelectTypePublic;
	}
	
	public void setBotonSelectType(Button botonSelectType) {
		BotonSelectType = botonSelectType;
	}
	
	public Label getLabelPrivPub() {
		return LabelPrivPub;
	}
	
	public void setLabelPrivPub(Label labelPrivPub) {
		LabelPrivPub = labelPrivPub;
	}
	
	public void setUpgradeable(Annotation A)
	{
	if ((!A.getUpdatability())&&(A.getUserId()!=ActualUser.getUser().getId())&&(ActualUser.getRol()==Constants.STUDENT))
		{
		BotonSelectTypePublic.setEnabled(false);
		BotonSelectType.setEnabled(false);
		richTextArea.setEnabled(false);
		}
	}
	
}
