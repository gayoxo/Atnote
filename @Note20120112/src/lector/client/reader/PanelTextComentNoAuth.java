package lector.client.reader;

import lector.client.controler.Constants;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.reader.PanelTextComentAuth.CatalogTipo;
import lector.client.reader.filter.advance.newRule;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

public class PanelTextComentNoAuth extends PanelTextComent{

	private RichTextArea richTextArea;
	private HorizontalPanel PenelBotonesTipo;
	private ScrollPanel scrollPanel;

	public PanelTextComentNoAuth() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		richTextArea = new RichTextArea();
		verticalPanel.add(richTextArea);
		richTextArea.setSize("100%", "100%");
		
		scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "");
		
		PenelBotonesTipo = new HorizontalPanel();
		scrollPanel.setWidget(PenelBotonesTipo);
		richTextArea.setEnabled(false);


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
		return new CheckBox();
	}
	
	public void setChckbxNewCheckBox(CheckBox chckbxNewCheckBox) {

	}
	
	public void setComboBox(ListBox comboBox) {

	}
	
	public ListBox getComboBox() {
		return new ListBox();
	}
	
	public RichTextToolbar getToolbar() {
		return new RichTextToolbar(richTextArea);
	}
	
	public void setToolbar(RichTextToolbar toolbar) {
	}
	
	public Button getBotonSelectType() {
		return new Button();
	}
	
	public Button getBotonSelectTypePublic() {
		return new Button();
	}
	
	public void setBotonSelectType(Button botonSelectType) {

	}
	
	public Label getLabelPrivPub() {
		return new Label();

	}
	
	public void setLabelPrivPub(Label labelPrivPub) {

	}
	
	public void setUpgradeable(Annotation A)
	{

		richTextArea.setEnabled(false);

	}
	
}
