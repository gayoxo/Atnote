package lector.client.admin.langedit.tabs;

import lector.client.admin.langedit.PanelDecorador;
import lector.client.admin.langedit.Texto;
import lector.client.language.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class MainWindowEditor extends PanelDecorador {

	private Texto NamePage;
	private Texto Specifications;
	private Texto Annotation;
	private Texto No_Annotation;
	private Texto All_Annotation;
	private Texto Only_Selected;
	private Texto BackAdministrationButton;
	private Texto BackUserButton;
    private Texto FilterMainButton;
    private Texto BrowserMainButton;
    private Texto AnnotationsFiltering;
    private Texto DOYOUFilterOUT;
    private Texto AcceptFilter;
    private Texto CancelFilter;
    private Texto ShowDensity;
	private static Language LenguajeActual;
	
	public MainWindowEditor(Language LenguajeActualin) {
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
		
		NamePage = new Texto(LenguajeActual.getNamePage());
		NamePage.getTextBox().addChangeHandler(CH);
		add(NamePage);
		Specifications = new Texto(LenguajeActual.getSpecifications());	
		Specifications.getTextBox().addChangeHandler(CH);
		add(Specifications);
		Annotation = new Texto(LenguajeActual.getAnnotation());
		Annotation.getTextBox().addChangeHandler(CH);
		add(Annotation);
		No_Annotation = new Texto(LenguajeActual.getNo_Annotation());	
		No_Annotation.getTextBox().addChangeHandler(CH);
		add(No_Annotation);
		All_Annotation = new Texto(LenguajeActual.getAll_Annotation());	
		All_Annotation.getTextBox().addChangeHandler(CH);
		add(All_Annotation);
		Only_Selected = new Texto(LenguajeActual.getOnly_Selected());		
		Only_Selected.getTextBox().addChangeHandler(CH);
		add(Only_Selected);
		BackAdministrationButton = new Texto(LenguajeActual.getBackAdministrationButton());		
		BackAdministrationButton.getTextBox().addChangeHandler(CH);
		add(BackAdministrationButton);
		BackUserButton = new Texto(LenguajeActual.getBackUserButton());	
		BackUserButton.getTextBox().addChangeHandler(CH);
		add(BackUserButton);
		FilterMainButton = new Texto(LenguajeActual.getFilterMainButton());	
		FilterMainButton.getTextBox().addChangeHandler(CH);
		add(FilterMainButton);
		BrowserMainButton = new Texto(LenguajeActual.getBrowserMainButton());	
		BrowserMainButton.getTextBox().addChangeHandler(CH);
		add(BrowserMainButton);
		AnnotationsFiltering = new Texto(LenguajeActual.getAnnotationsFiltering());	
		AnnotationsFiltering.getTextBox().addChangeHandler(CH);
		add(AnnotationsFiltering);
		DOYOUFilterOUT = new Texto(LenguajeActual.getDOYOUFilterOUT());	
		DOYOUFilterOUT.getTextBox().addChangeHandler(CH);
		add(DOYOUFilterOUT);
		AcceptFilter = new Texto(LenguajeActual.getAcceptFilter());	
		AcceptFilter.getTextBox().addChangeHandler(CH);
		add(AcceptFilter);
		CancelFilter = new Texto(LenguajeActual.getCancelFilter());	
		CancelFilter.getTextBox().addChangeHandler(CH);
		add(CancelFilter);
		ShowDensity = new Texto(LenguajeActual.getShowDensity());	
		ShowDensity.getTextBox().addChangeHandler(CH);
		add(ShowDensity);
		

	}

	public void saveAll() {
		
		
		
		if (!NamePage.getText().isEmpty())
			if (NamePage.getText().length()<2 ) Window.alert(NamePage.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setNamePage(NamePage.getText());
		
		if (!Specifications.getText().isEmpty())
			if (Specifications.getText().length()<2 ) Window.alert(Specifications.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setSpecifications(Specifications.getText());
		
		if (!Annotation.getText().isEmpty())
			if (Annotation.getText().length()<2 ) Window.alert(Annotation.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAnnotation(Annotation.getText());
		
		if (!No_Annotation.getText().isEmpty())
			if (No_Annotation.getText().length()<2 ) Window.alert(No_Annotation.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setNo_Annotation(No_Annotation.getText());
		
		if (!All_Annotation.getText().isEmpty())
			if (All_Annotation.getText().length()<2 ) Window.alert(All_Annotation.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAll_Annotation(All_Annotation.getText());
		
		if (!Only_Selected.getText().isEmpty())
			if (Only_Selected.getText().length()<2 ) Window.alert(Only_Selected.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setOnly_Selected(Only_Selected.getText());
		
		if (!BackAdministrationButton.getText().isEmpty())
			if (BackAdministrationButton.getText().length()<2 ) Window.alert(BackAdministrationButton.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setBackAdministrationButton(BackAdministrationButton.getText());
		
		if (!BackUserButton.getText().isEmpty())
			if (BackUserButton.getText().length()<2 ) Window.alert(BackUserButton.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setBackUserButton(BackUserButton.getText());
		
		if (!FilterMainButton.getText().isEmpty())
			if (FilterMainButton.getText().length()<2 ) Window.alert(FilterMainButton.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFilterMainButton(FilterMainButton.getText());
		
		if (!BrowserMainButton.getText().isEmpty())
			if (BrowserMainButton.getText().length()<2 ) Window.alert(BrowserMainButton.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setBrowserMainButton(BrowserMainButton.getText());
		
		if (!AnnotationsFiltering.getText().isEmpty())
			if (AnnotationsFiltering.getText().length()<2 ) Window.alert(AnnotationsFiltering.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAnnotationsFiltering(AnnotationsFiltering.getText());
		
		if (!DOYOUFilterOUT.getText().isEmpty())
			if (DOYOUFilterOUT.getText().length()<2 ) Window.alert(DOYOUFilterOUT.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setDOYOUFilterOUT(DOYOUFilterOUT.getText());
		
		if (!AcceptFilter.getText().isEmpty())
			if (AcceptFilter.getText().length()<2 ) Window.alert(AcceptFilter.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setAcceptFilter(AcceptFilter.getText());
		
		if (!CancelFilter.getText().isEmpty())
			if (CancelFilter.getText().length()<2 ) Window.alert(CancelFilter.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setCancelFilter(CancelFilter.getText());
		
		if (!ShowDensity.getText().isEmpty())
			if (ShowDensity.getText().length()<2 ) Window.alert(ShowDensity.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setShowDensity(ShowDensity.getText());
		
		saveLanguage(LenguajeActual);
		saved=true;
	}
}
