package lector.client.admin.langedit;

import lector.client.language.Language;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BottonLang extends Button {

	private Language language;
	private VerticalPanel Actual;
	private VerticalPanel Normal;
	private VerticalPanel Selected;
	
	public BottonLang(VerticalPanel Normalin,VerticalPanel Selectedin,Language language) {
		super(language.getName());
		this.language=language;
		Normal=Normalin;
		Selected=Selectedin;
		Actual=Normal;
		Actual.add(this);
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	
	public void swap()
	{
		if (Actual==Normal)
			{
			Actual.remove(this);
			Actual=Selected;
			Selected.add(this);
			}
		else if (Actual==Selected)
			{
			Selected.remove(this);
			Actual=Normal;
			Normal.add(this);
			}
	}
	
}
