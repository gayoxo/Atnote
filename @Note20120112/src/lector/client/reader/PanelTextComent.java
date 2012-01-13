package lector.client.reader;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;

public abstract class PanelTextComent extends Composite{
	public PanelTextComent() {
	}



	
	public abstract void setRichTextArea(RichTextArea richTextArea);
	
	public abstract RichTextArea getRichTextArea();
	
	public abstract HorizontalPanel getPenelBotonesTipo();
	
	public abstract void setPenelBotonesTipo(HorizontalPanel penelBotonesTipo);
	
	public abstract CheckBox getChckbxNewCheckBox();
	
	public abstract void setChckbxNewCheckBox(CheckBox chckbxNewCheckBox);
	
	public abstract void setComboBox(ListBox comboBox);
	
	public abstract ListBox getComboBox();
	
	public abstract RichTextToolbar getToolbar();
	
	public abstract void setToolbar(RichTextToolbar toolbar);
	
	public abstract Button getBotonSelectType();
	
	public abstract Button getBotonSelectTypePublic();
	
	public abstract void setBotonSelectType(Button botonSelectType);
	
	public abstract Label getLabelPrivPub();
	
	public abstract void setLabelPrivPub(Label labelPrivPub);
	
	public abstract void setUpgradeable(Annotation A);
}
