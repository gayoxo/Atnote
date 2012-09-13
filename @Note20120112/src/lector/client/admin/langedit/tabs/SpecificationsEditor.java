package lector.client.admin.langedit.tabs;

import lector.client.admin.langedit.PanelDecorador;
import lector.client.admin.langedit.Texto;
import lector.share.model.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class SpecificationsEditor extends PanelDecorador {

	private Texto ID;
	private Texto Authors;
	private Texto Pages;
	private Texto Publication_Year;
	private Texto Title;
	private Texto Front_Cover;
	private static Language LenguajeActual;
	
	public SpecificationsEditor(Language LenguajeActualin) {
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
		
		ID = new Texto(LenguajeActual.getID());
		ID.getTextBox().addChangeHandler(CH);
		add(ID);
		Authors = new Texto(LenguajeActual.getAuthors());	
		Authors.getTextBox().addChangeHandler(CH);
		add(Authors);
		Pages = new Texto(LenguajeActual.getPages());
		Pages.getTextBox().addChangeHandler(CH);
		add(Pages);
		Publication_Year = new Texto(LenguajeActual.getPublication_Year());	
		Publication_Year.getTextBox().addChangeHandler(CH);
		add(Publication_Year);
		Title = new Texto(LenguajeActual.getTitle());	
		Title.getTextBox().addChangeHandler(CH);
		add(Title);
		Front_Cover = new Texto(LenguajeActual.getFront_Cover());	
		Front_Cover.getTextBox().addChangeHandler(CH);
		add(Front_Cover);
		

	}

	public void saveAll() {
		
		if (!ID.getText().isEmpty())
			if (ID.getText().length()<2 ) Window.alert(ID.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setID(ID.getText());
		
		if (!Authors.getText().isEmpty())
			if (Authors.getText().length()<2 ) Window.alert(Authors.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAuthors(Authors.getText());
		
		if (!Pages.getText().isEmpty())
			if (Pages.getText().length()<2 ) Window.alert(Pages.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setPages(Pages.getText());
		
		if (!Publication_Year.getText().isEmpty())
			if (Publication_Year.getText().length()<2 ) Window.alert(Publication_Year.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setPublication_Year(Publication_Year.getText());
		
		if (!Title.getText().isEmpty())
			if (Title.getText().length()<2 ) Window.alert(Title.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setTitle(Title.getText());	
		
		if (!Front_Cover.getText().isEmpty())
			if (Front_Cover.getText().length()<2 ) Window.alert(Front_Cover.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFront_Cover(Front_Cover.getText());	
		
		saveLanguage(LenguajeActual);
		saved=true;
	}
}
