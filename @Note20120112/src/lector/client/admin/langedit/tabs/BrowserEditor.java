package lector.client.admin.langedit.tabs;

import lector.client.admin.langedit.PanelDecorador;
import lector.client.admin.langedit.Texto;
import lector.client.language.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class BrowserEditor extends PanelDecorador {

	private static Language LenguajeActual;
	 
		private Texto Close;
	    private Texto FilterButtonBrowser;
	    private Texto GO_To_Page;
	    private Texto Comment_Area;
	    private Texto Page;
	    private Texto TeacherTypes;
		private Texto OpenTypes;
	
	public BrowserEditor(Language LenguajeActualin) {
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
		
		Close = new Texto(LenguajeActual.getClose());
		Close.getTextBox().addChangeHandler(CH);
		add(Close);
		
		FilterButtonBrowser = new Texto(LenguajeActual.getFilterButtonBrowser());	
		FilterButtonBrowser.getTextBox().addChangeHandler(CH);
		add(FilterButtonBrowser);
		
		GO_To_Page = new Texto(LenguajeActual.getGO_To_Page());	
		GO_To_Page.getTextBox().addChangeHandler(CH);
		add(GO_To_Page);
		
		Comment_Area = new Texto(LenguajeActual.getComment_Area());		
		Comment_Area.getTextBox().addChangeHandler(CH);
		add(Comment_Area);
		
		Page = new Texto(LenguajeActual.getPage());		
		Page.getTextBox().addChangeHandler(CH);
		add(Page);
		
		OpenTypes = new Texto(LenguajeActual.getOpenTypes());		
		OpenTypes.getTextBox().addChangeHandler(CH);
		add(OpenTypes);
		
		TeacherTypes = new Texto(LenguajeActual.getTeacherTypes());		
		TeacherTypes.getTextBox().addChangeHandler(CH);
		add(TeacherTypes);
		
		
	}

	public void saveAll() {
		
		
	    
		if (!Close.getText().isEmpty())
			if (Close.getText().length()<2 ) Window.alert(Close.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setClose(Close.getText());
		

		
		if (!FilterButtonBrowser.getText().isEmpty())
			if (FilterButtonBrowser.getText().length()<2 ) Window.alert(FilterButtonBrowser.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFilterButtonBrowser(FilterButtonBrowser.getText());
		
		if (!GO_To_Page.getText().isEmpty())
			if (GO_To_Page.getText().length()<2 ) Window.alert(GO_To_Page.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setGO_To_Page(GO_To_Page.getText());
		
		if (!Comment_Area.getText().isEmpty())
			if (Comment_Area.getText().length()<2 ) Window.alert(Comment_Area.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setComment_Area(Comment_Area.getText());
		
		if (!Page.getText().isEmpty())
			if (Page.getText().length()<2 ) Window.alert(Page.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setPage(Page.getText());
		
		if (!TeacherTypes.getText().isEmpty())
			if (TeacherTypes.getText().length()<2 ) Window.alert(TeacherTypes.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setTeacherTypes(TeacherTypes.getText());
		
		if (!OpenTypes.getText().isEmpty())
			if (OpenTypes.getText().length()<2 ) Window.alert(OpenTypes.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setOpenTypes(OpenTypes.getText());
		
		saveLanguage(LenguajeActual);
		saved=true;
	}
}
