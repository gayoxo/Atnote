package lector.client.admin.activity.buttons;

import lector.client.language.Language;

import com.google.gwt.user.client.ui.Button;

public class Botonlanguage extends Button {
 
	
	private Language language;
	public Botonlanguage(Language languagein) {
		super(languagein.getName());
		language=languagein;
		
	}
	
public Language getLanguage() {
	return language;
}

public void setLanguage(Language language) {
	this.language = language;
}


}
