package lector.client.reader.filter.advance;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.browser.CommentPanelBrowser;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.Annotation;


public class FilterAsyncSystem {

	private static ArrayList<Rule> ReglasEvaluar;
	private static int actualRule=0;
	private static ArrayList<Annotation> Anotaciones;
	private static ArrayList<Long> filtroResidual;
	
	public FilterAsyncSystem(ArrayList<Rule> Reglas) {
		//TODO Mensajes Generales
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getFiltering());
		ReglasEvaluar=Reglas;
		actualRule=0;
		Anotaciones=new ArrayList<Annotation>();
		filtroResidual=new ArrayList<Long>();
		nextRule();
	}
	
	public static void nextRule()
	{
		if (actualRule<ReglasEvaluar.size())
		{
			ReglasEvaluar.get(actualRule).evaluarReglas();
		}else flitrado();
		actualRule++;
	}
	
	private static void flitrado() {
		generafiltroResidual();
		VerticalPanel resultado=new VerticalPanel();
		AnotationFilterResultPanel Panel=new AnotationFilterResultPanel(resultado);
		for (Annotation AIndiv : Anotaciones) {
			resultado.add(new CommentPanelFAdvance(AIndiv, new Image(ActualUser.getBook().getWebLinks().get(AIndiv.getPageNumber())),Panel.getHeight()));
		}	
		Panel.center();
		LoadingPanel.getInstance().hide();
		
	}

	private static void generafiltroResidual() {
		filtroResidual=new ArrayList<Long>();
		for (Annotation AIndiv : Anotaciones)
		{
			filtroResidual.add(AIndiv.getId());
		}
		
	}

	public static ArrayList<Annotation> getAnotaciones() {
		return Anotaciones;
	}
	
	public static void setAnotaciones(ArrayList<Annotation> anotaciones) {
		Anotaciones = anotaciones;
	}

	public static ArrayList<Long> getFiltroResidual() {
		return filtroResidual;
	}
}
