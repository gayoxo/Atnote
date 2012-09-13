package lector.client.admin.langedit.tabs;

import lector.client.admin.langedit.PanelDecorador;
import lector.client.admin.langedit.Texto;
import lector.share.model.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class VariosEditor extends PanelDecorador {

	private static Language LenguajeActual;
	private Texto E_Page_Dont_Exist;
    private Texto E_Not_a_number;
    private Texto Loading;
    private Texto Saving;
    private Texto Deleting;
    private Texto Filtering;
    private Texto E_Coments_dont_be_refresh;
    private Texto E_Need_to_select_a_type;
    private Texto W_Newer_version_of_anotation;
    private Texto E_Tags_Refresh;
    private Texto E_Types_refresh;
    private Texto E_Empty_Or_2Less_Tag;
    private Texto E_Tag_Dont_Exist;
    private Texto E_loading;
    private Texto E_saving;
    private Texto E_deleting;
    private Texto E_WasBefore;
    private Texto E_typeFilter;
    private Texto E_filteringmesagetypes;
    private Texto E_filteringmesageAnnotations;
    private Texto E_Saving;
	private Texto E_DeleteReply;
	
	public VariosEditor(Language LenguajeActualin) {
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
		
		E_Page_Dont_Exist = new Texto(LenguajeActual.getE_Page_Dont_Exist());
		E_Page_Dont_Exist.getTextBox().addChangeHandler(CH);
		add(E_Page_Dont_Exist);
		
		E_Not_a_number = new Texto(LenguajeActual.getE_Not_a_number());	
		E_Not_a_number.getTextBox().addChangeHandler(CH);
		add(E_Not_a_number);
		
		Loading = new Texto(LenguajeActual.getLoading());
		Loading.getTextBox().addChangeHandler(CH);
		add(Loading);
		
		Saving = new Texto(LenguajeActual.getSaving());	
		Saving.getTextBox().addChangeHandler(CH);
		add(Saving);
		
		Deleting = new Texto(LenguajeActual.getDeleting());	
		Deleting.getTextBox().addChangeHandler(CH);
		add(Deleting);
		
		Filtering = new Texto(LenguajeActual.getFiltering());	
		Filtering.getTextBox().addChangeHandler(CH);
		add(Filtering);
		
		E_Coments_dont_be_refresh = new Texto(LenguajeActual.getE_Coments_dont_be_refresh());		
		E_Coments_dont_be_refresh.getTextBox().addChangeHandler(CH);
		add(E_Coments_dont_be_refresh);
		
		E_Need_to_select_a_type = new Texto(LenguajeActual.getE_Need_to_select_a_type());		
		E_Need_to_select_a_type.getTextBox().addChangeHandler(CH);
		add(E_Need_to_select_a_type);
		
		W_Newer_version_of_anotation = new Texto(LenguajeActual.getW_Newer_version_of_anotation());	
		W_Newer_version_of_anotation.getTextBox().addChangeHandler(CH);
		add(W_Newer_version_of_anotation);
		
		E_Tags_Refresh = new Texto(LenguajeActual.getE_Tags_Refresh());	
		E_Tags_Refresh.getTextBox().addChangeHandler(CH);
		add(E_Tags_Refresh);
		
		E_Types_refresh = new Texto(LenguajeActual.getE_Types_refresh());	
		E_Types_refresh.getTextBox().addChangeHandler(CH);
		add(E_Types_refresh);
		
		E_Empty_Or_2Less_Tag = new Texto(LenguajeActual.getE_Empty_Or_2Less_Tag());	
		E_Empty_Or_2Less_Tag.getTextBox().addChangeHandler(CH);
		add(E_Empty_Or_2Less_Tag);
		
		E_Tag_Dont_Exist = new Texto(LenguajeActual.getE_Tag_Dont_Exist());	
		E_Tag_Dont_Exist.getTextBox().addChangeHandler(CH);
		add(E_Tag_Dont_Exist);
		
		E_loading = new Texto(LenguajeActual.getE_loading());	
		E_loading.getTextBox().addChangeHandler(CH);
		add(E_loading);
		
		E_saving = new Texto(LenguajeActual.getE_saving());	
		E_saving.getTextBox().addChangeHandler(CH);
		add(E_saving);
		
		E_deleting = new Texto(LenguajeActual.getE_deleting());	
		E_deleting.getTextBox().addChangeHandler(CH);
		add(E_deleting);
		
		E_WasBefore = new Texto(LenguajeActual.getE_ExistBefore());	
		E_WasBefore.getTextBox().addChangeHandler(CH);
		add(E_WasBefore);
		
		E_typeFilter = new Texto(LenguajeActual.getE_typeFilter());	
		E_typeFilter.getTextBox().addChangeHandler(CH);
		add(E_typeFilter);
		
		E_filteringmesagetypes = new Texto(LenguajeActual.getE_filteringmesagetypes());	
		E_filteringmesagetypes.getTextBox().addChangeHandler(CH);
		add(E_filteringmesagetypes);
		
		E_filteringmesageAnnotations = new Texto(LenguajeActual.getE_filteringmesageAnnotations());
		E_filteringmesageAnnotations.getTextBox().addChangeHandler(CH);
		add(E_filteringmesageAnnotations);
		
		E_Saving = new Texto(LenguajeActual.getE_Saving());
		E_Saving.getTextBox().addChangeHandler(CH);
		add(E_Saving);
		
		E_DeleteReply = new Texto(LenguajeActual.getE_DeleteReply());
		E_DeleteReply.getTextBox().addChangeHandler(CH);
		add(E_DeleteReply);

	}

	public void saveAll() {
		
		
	    
		if (!E_Page_Dont_Exist.getText().isEmpty())
			if (E_Page_Dont_Exist.getText().length()<2 ) Window.alert(E_Page_Dont_Exist.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Page_Dont_Exist(E_Page_Dont_Exist.getText());
		
		if (!E_Not_a_number.getText().isEmpty())
			if (E_Not_a_number.getText().length()<2 ) Window.alert(E_Not_a_number.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Not_a_number(E_Not_a_number.getText());
		
		if (!Loading.getText().isEmpty())
			if (Loading.getText().length()<2 ) Window.alert(Loading.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setLoading(Loading.getText());
		
		if (!Saving.getText().isEmpty())
			if (Saving.getText().length()<2 ) Window.alert(Saving.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setSaving(Saving.getText());
		
		if (!Deleting.getText().isEmpty())
			if (Deleting.getText().length()<2 ) Window.alert(Deleting.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setDeleting(Deleting.getText());
		
		if (!Filtering.getText().isEmpty())
			if (Filtering.getText().length()<2 ) Window.alert(Filtering.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setFiltering(Filtering.getText());
		
		if (!E_Coments_dont_be_refresh.getText().isEmpty())
			if (E_Coments_dont_be_refresh.getText().length()<2 ) Window.alert(E_Coments_dont_be_refresh.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Coments_dont_be_refresh(E_Coments_dont_be_refresh.getText());
		
		if (!E_Need_to_select_a_type.getText().isEmpty())
			if (E_Need_to_select_a_type.getText().length()<2 ) Window.alert(E_Need_to_select_a_type.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Need_to_select_a_type(E_Need_to_select_a_type.getText());
		
		if (!W_Newer_version_of_anotation.getText().isEmpty())
			if (W_Newer_version_of_anotation.getText().length()<2 ) Window.alert(W_Newer_version_of_anotation.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setW_Newer_version_of_anotation(W_Newer_version_of_anotation.getText());
		
		if (!E_Tags_Refresh.getText().isEmpty())
			if (E_Tags_Refresh.getText().length()<2 ) Window.alert(E_Tags_Refresh.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Tags_Refresh(E_Tags_Refresh.getText());
		
		if (!E_Types_refresh.getText().isEmpty())
			if (E_Types_refresh.getText().length()<2 ) Window.alert(E_Types_refresh.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Types_refresh(E_Types_refresh.getText());
		
		if (!E_Empty_Or_2Less_Tag.getText().isEmpty())
			if (E_Empty_Or_2Less_Tag.getText().length()<2 ) Window.alert(E_Empty_Or_2Less_Tag.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Empty_Or_2Less_Tag(E_Empty_Or_2Less_Tag.getText());
		
		if (!E_Tag_Dont_Exist.getText().isEmpty())
			if (E_Tag_Dont_Exist.getText().length()<2 ) Window.alert(E_Tag_Dont_Exist.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Tag_Dont_Exist(E_Tag_Dont_Exist.getText());
		
		if (!E_loading.getText().isEmpty())
			if (E_loading.getText().length()<2 ) Window.alert(E_loading.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_loading(E_loading.getText());
		
		if (!E_saving.getText().isEmpty())
			if (E_saving.getText().length()<2 ) Window.alert(E_saving.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_saving(E_saving.getText());
		
		if (!E_deleting.getText().isEmpty())
			if (E_deleting.getText().length()<2 ) Window.alert(E_deleting.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_deleting(E_deleting.getText());
		
		if (!E_WasBefore.getText().isEmpty())
			if (E_WasBefore.getText().length()<2 ) Window.alert(E_WasBefore.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_ExistBefore(E_WasBefore.getText());
		
		if (!E_typeFilter.getText().isEmpty())
			if (E_typeFilter.getText().length()<2 ) Window.alert(E_typeFilter.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_typeFilter(E_typeFilter.getText());
		
		if (!E_filteringmesagetypes.getText().isEmpty())
			if (E_filteringmesagetypes.getText().length()<2 ) Window.alert(E_filteringmesagetypes.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_filteringmesagetypes(E_filteringmesagetypes.getText());
		
		if (!E_filteringmesageAnnotations.getText().isEmpty())
			if (E_filteringmesageAnnotations.getText().length()<2 ) Window.alert(E_filteringmesageAnnotations.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_filteringmesageAnnotations(E_filteringmesageAnnotations.getText());
		
		if (!E_Saving.getText().isEmpty())
			if (E_Saving.getText().length()<2 ) Window.alert(E_Saving.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_Saving(E_Saving.getText());
		
		if (!E_DeleteReply.getText().isEmpty())
			if (E_DeleteReply.getText().length()<2 ) Window.alert(E_DeleteReply.getLabel() + " lengh need to be more lenght tham two");
				else LenguajeActual.setE_DeleteReply(E_DeleteReply.getText());
		
		saveLanguage(LenguajeActual);
		saved=true;
	}
}
