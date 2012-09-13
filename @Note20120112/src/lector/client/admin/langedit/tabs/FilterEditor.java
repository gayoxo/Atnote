package lector.client.admin.langedit.tabs;

import lector.client.admin.langedit.PanelDecorador;
import lector.client.admin.langedit.Texto;
import lector.share.model.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class FilterEditor extends PanelDecorador {

	private Texto Advance;
	private Texto FilterButton;
	private Texto Select_All;
	private Texto All_Selected;
	
	 private Texto CloseFA;
	    private Texto FilterButtonFA;
	    private Texto New_Rule;
	    private Texto Remove;
	    private Texto GO_To_PageFA;
	    private Texto Comment_AreaFA;
	    private Texto PageFA;
	    private Texto Rule;
	    
	private static Language LenguajeActual;
	
	public FilterEditor(Language LenguajeActualin) {
		ChangeHandler CH=new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				saved=false;
			}
		};
		
		LenguajeActual=LenguajeActualin;
		setSpacing(6);
		saved=true;
		setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setSize("100%", "600px");
		
		Advance = new Texto(LenguajeActual.getAdvance());
		Advance.getTextBox().addChangeHandler(CH);
		add(Advance);
		FilterButton = new Texto(LenguajeActual.getFilterButton());
		FilterButton.getTextBox().addChangeHandler(CH);
		add(FilterButton);
		Select_All = new Texto(LenguajeActual.getSelect_All());	
		Select_All.getTextBox().addChangeHandler(CH);
		add(Select_All);
		All_Selected = new Texto(LenguajeActual.getAllSelected());	
		All_Selected.getTextBox().addChangeHandler(CH);
		add(All_Selected);
		CloseFA = new Texto(LenguajeActual.getCloseFA());	
		CloseFA.getTextBox().addChangeHandler(CH);
		add(CloseFA);
		FilterButtonFA = new Texto(LenguajeActual.getFilterButtonFA());	
		FilterButtonFA.getTextBox().addChangeHandler(CH);
		add(FilterButtonFA);
		New_Rule = new Texto(LenguajeActual.getNew_Rule());	
		New_Rule.getTextBox().addChangeHandler(CH);
		add(New_Rule);
		Remove = new Texto(LenguajeActual.getRemove());	
		Remove.getTextBox().addChangeHandler(CH);
		add(Remove);
		GO_To_PageFA = new Texto(LenguajeActual.getGO_To_PageFA());	
		GO_To_PageFA.getTextBox().addChangeHandler(CH);
		add(GO_To_PageFA);
		Comment_AreaFA = new Texto(LenguajeActual.getComment_AreaFA());	
		Comment_AreaFA.getTextBox().addChangeHandler(CH);
		add(Comment_AreaFA);
		PageFA = new Texto(LenguajeActual.getPageFA());	
		PageFA.getTextBox().addChangeHandler(CH);
		add(PageFA);
		Rule = new Texto(LenguajeActual.getRule());	
		Rule.getTextBox().addChangeHandler(CH);
		add(Rule);
		

	}

	public void saveAll() {
		
		
		
		if (!Advance.getText().isEmpty())
			if (Advance.getText().length()<2 ) Window.alert(Advance.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAdvance(Advance.getText());
		
		
		if (!FilterButton.getText().isEmpty())
			if (FilterButton.getText().length()<2 ) Window.alert(FilterButton.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFilterButton(FilterButton.getText());
		
		if (!Select_All.getText().isEmpty())
			if (Select_All.getText().length()<2 ) Window.alert(Select_All.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setSelect_All(Select_All.getText());
		
		if (!All_Selected.getText().isEmpty())
			if (All_Selected.getText().length()<2 ) Window.alert(All_Selected.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAllSelected(All_Selected.getText());
		
		if (!CloseFA.getText().isEmpty())
			if (CloseFA.getText().length()<2 ) Window.alert(CloseFA.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setCloseFA(CloseFA.getText());
		
		if (!FilterButtonFA.getText().isEmpty())
			if (FilterButtonFA.getText().length()<2 ) Window.alert(FilterButtonFA.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFilterButtonFA(FilterButtonFA.getText());
		
		if (!New_Rule.getText().isEmpty())
			if (New_Rule.getText().length()<2 ) Window.alert(New_Rule.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setNew_Rule(New_Rule.getText());
		
		if (!Remove.getText().isEmpty())
			if (Remove.getText().length()<2 ) Window.alert(Remove.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setRemove(Remove.getText());
		
		if (!GO_To_PageFA.getText().isEmpty())
			if (GO_To_PageFA.getText().length()<2 ) Window.alert(GO_To_PageFA.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setGO_To_PageFA(GO_To_PageFA.getText());
		
		if (!Comment_AreaFA.getText().isEmpty())
			if (Comment_AreaFA.getText().length()<2 ) Window.alert(Comment_AreaFA.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setComment_AreaFA(Comment_AreaFA.getText());
		
		if (!PageFA.getText().isEmpty())
			if (PageFA.getText().length()<2 ) Window.alert(PageFA.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setPageFA(PageFA.getText());
		
		if (!Rule.getText().isEmpty())
			if (Rule.getText().length()<2 ) Window.alert(Rule.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setRule(Rule.getText());
		
		
		saveLanguage(LenguajeActual);
		saved=true;
	}
}
