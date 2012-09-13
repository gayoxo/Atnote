package lector.client.admin.langedit.tabs;

import lector.client.admin.langedit.PanelDecorador;
import lector.client.admin.langedit.Texto;
import lector.share.model.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class AnnotationEditor extends PanelDecorador {

	private Texto Save;
	private Texto ClearAnot;
	private Texto Cancel;
	private Texto Delete;
	private Texto Types;
	private Texto SetTypes;
	private Texto Visibility;
	private Texto Upgradeable;
	private Texto New;
	private Texto FromExist;
	private Texto NewAdmin;
	private Texto Done;
	private static Language LenguajeActual;
	
	public AnnotationEditor(Language LenguajeActualin) {
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
		
		Save = new Texto(LenguajeActual.getSave());
		Save.getTextBox().addChangeHandler(CH);
		add(Save);
		ClearAnot = new Texto(LenguajeActual.getClearAnot());	
		ClearAnot.getTextBox().addChangeHandler(CH);
		add(ClearAnot);
		Cancel = new Texto(LenguajeActual.getCancel());
		Cancel.getTextBox().addChangeHandler(CH);
		add(Cancel);
		Delete = new Texto(LenguajeActual.getDeleteAnnotation());	
		Delete.getTextBox().addChangeHandler(CH);
		add(Delete);
		Types = new Texto(LenguajeActual.getSetTypesPublic());		
		Types.getTextBox().addChangeHandler(CH);
		add(Types);
		SetTypes = new Texto(LenguajeActual.getSetTypes());		
		SetTypes.getTextBox().addChangeHandler(CH);
		add(SetTypes);
		Visibility = new Texto(LenguajeActual.getVisibility());	
		Visibility.getTextBox().addChangeHandler(CH);
		add(Visibility);
		Upgradeable = new Texto(LenguajeActual.getUpgradeable());	
		Upgradeable.getTextBox().addChangeHandler(CH);
		add(Upgradeable);
		New = new Texto(LenguajeActual.getNew());	
		New.getTextBox().addChangeHandler(CH);
		add(New);
		FromExist = new Texto(LenguajeActual.getFromExist());	
		FromExist.getTextBox().addChangeHandler(CH);
		add(FromExist);
		NewAdmin = new Texto(LenguajeActual.getNewAdmin());	
		NewAdmin.getTextBox().addChangeHandler(CH);
		add(NewAdmin);
		Done = new Texto(LenguajeActual.getDone());	
		Done.getTextBox().addChangeHandler(CH);
		add(Done);
		
		

	}

	public void saveAll() {
		
		
		
		if (!Save.getText().isEmpty())
			if (Save.getText().length()<2 ) Window.alert(Save.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setSave(Save.getText());
		
		if (!ClearAnot.getText().isEmpty())
			if (ClearAnot.getText().length()<2 ) Window.alert(ClearAnot.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setClearAnot(ClearAnot.getText());
		
		if (!Cancel.getText().isEmpty())
			if (Cancel.getText().length()<2 ) Window.alert(Cancel.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setCancel(Cancel.getText());
		
		if (!Delete.getText().isEmpty())
			if (Delete.getText().length()<2 ) Window.alert(Delete.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setDeleteAnnotation(Delete.getText());
		
		
		if (!Types.getText().isEmpty())
			if (Types.getText().length()<2 ) Window.alert(Types.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setSetTypesPublic(Types.getText());
		
		if (!SetTypes.getText().isEmpty())
			if (SetTypes.getText().length()<2 ) Window.alert(SetTypes.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setSetTypes(SetTypes.getText());
		
		if (!Visibility.getText().isEmpty())
			if (Visibility.getText().length()<2 ) Window.alert(Visibility.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setVisibility(Visibility.getText());
		
		if (!Upgradeable.getText().isEmpty())
			if (Upgradeable.getText().length()<2 ) Window.alert(Upgradeable.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setUpgradeable(Upgradeable.getText());
		
		if (!New.getText().isEmpty())
			if (New.getText().length()<2 ) Window.alert(New.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setNew(New.getText());
		
		if (!FromExist.getText().isEmpty())
			if (FromExist.getText().length()<2 ) Window.alert(FromExist.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFromExist(FromExist.getText());
		
		if (!NewAdmin.getText().isEmpty())
			if (NewAdmin.getText().length()<2 ) Window.alert(NewAdmin.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setNewAdmint(NewAdmin.getText());
		
		if (!Done.getText().isEmpty())
			if (Done.getText().length()<2 ) Window.alert(Done.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setDone(Done.getText());
		
		saveLanguage(LenguajeActual);
		saved=true;
	}
}
