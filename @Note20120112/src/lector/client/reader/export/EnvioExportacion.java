package lector.client.reader.export;

import java.util.ArrayList;

import lector.client.reader.Annotation;
import lector.client.reader.TextSelector;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.gen2.commonwidget.client.DecoratorPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

public class EnvioExportacion extends Composite {

	private Annotation Annotation;
	private Image Imagen;
	private VerticalPanel Imagenes;
	
	public EnvioExportacion(Annotation annotation,Image imagen) {
		
		Annotation=annotation;
		Imagen=imagen;
		//com.google.gwt.user.client.ui.DecoratorPanel decoratorPanel = new com.google.gwt.user.client.ui.DecoratorPanel();
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		
		Imagenes = new VerticalPanel();
		horizontalPanel.add(Imagenes);
		
		
		HTML htmlNewHtml = new HTML(Annotation.getComment().toString(), true);
		horizontalPanel.add(htmlNewHtml);
		
		
		
		ArrayList<TextSelector> TS=Annotation.getTextSelectors();
		for (TextSelector textSelector : TS) {
			String url=Imagen.getUrl();
			if (url.contains("serve?blob"))
			{
			String[] Safe=url.split("serve");
			StringBuffer SB=new StringBuffer();
			SB.append("/serve");
			for (int i = 1; i < Safe.length; i++) {
				SB.append(Safe[i]);
				
			}
			url=SB.toString();
			}
			ImageNueva I = new ImageNueva(url,textSelector);
		//	I.setVisibleRect(textSelector.getX().intValue(), textSelector.getY().intValue(), (int)(textSelector.getX()+textSelector.getWidth()), (int)(textSelector.getY()+textSelector.getHeight()));
			Imagenes.add(I);
			}
			
		}
		
		
	
}
