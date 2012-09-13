package lector.client.reader.export;

import java.util.ArrayList;

import lector.share.model.Annotation;
import lector.share.model.TextSelector;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.gen2.commonwidget.client.DecoratorPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.FlowPanel;

public class EnvioExportacion extends Composite {

	private Annotation Annotation;
	private Image Imagen;
	private VerticalPanel Imagenes;
	
	public EnvioExportacion(Annotation annotation,Image imagen) {
		
		Annotation=annotation;
		Imagen=imagen;
		//com.google.gwt.user.client.ui.DecoratorPanel decoratorPanel = new com.google.gwt.user.client.ui.DecoratorPanel();
		
		VerticalPanel V=new VerticalPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		initWidget(V);
		V.setSize("100%", "100%");
		V.add(horizontalPanel);
		
		Imagenes = new VerticalPanel();
		horizontalPanel.add(Imagenes);
		
		
		HTML htmlNewHtml = new HTML(Annotation.getComment().toString(), true);
		horizontalPanel.add(htmlNewHtml);
		
		HTML htmlNewHtml_1 = new HTML("<hr  size=\u201D9\u2033 />", true);
		V.add(htmlNewHtml_1);
		htmlNewHtml_1.setSize("100%", "");
		
		
		
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
