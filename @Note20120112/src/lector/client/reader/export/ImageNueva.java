package lector.client.reader.export;

import lector.client.reader.SelectorPanel;
import lector.share.model.TextSelector;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;

public class ImageNueva extends Image {

	private TextSelector TextSelector;
	private boolean recorte=false;
	private float prop;
	
	public ImageNueva(String url, TextSelector textSelector) {
		super(url);
//		if (url.contains("serve?blob"))
//			{
//			String[] Safe=url.split("server");
//			StringBuffer SB=new StringBuffer();
//			for (int i = 1; i < Safe.length; i++) {
//				SB.append(Safe[i]);
//				
//			}
//			url=SB.toString();
//			}
		
		TextSelector=textSelector;
		addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				if (!recorte)
				{
				Image I = (Image) event.getSource();
				float He = I.getHeight();
				float Wi = I.getWidth();
				prop = He / 830;

				
				float X=(float)TextSelector.getX()*prop;
				float Y=(float)TextSelector.getY()*prop;
				float WHP=(float)(TextSelector.getWidth())*prop;
				float HHP=(float)(TextSelector.getHeight())*prop;
				
				
//				float X=(float)TextSelector.getX();
//				//*prop;
//		float Y=(float)TextSelector.getY();//*prop;
//		float WHP=(float)(TextSelector.getWidth());//*prop;
//		float HHP=(float)(TextSelector.getHeight());//*prop;
//		
		
//				SelectorPanel SEE = new SelectorPanel(Math.round(X),Math.round(Y),
//                        I.getAbsoluteLeft(), I.getAbsoluteTop(),
//                        Math.round(WHP),Math.round(HHP));
//                SEE.show();
				setVisibleRect(Math.round(X),Math.round(Y),Math.round(WHP),Math.round(HHP));
				


				
//				I.setVisibleRect(TextSelector.getX().intValue(), TextSelector.getY().intValue(), (int)(TextSelector.getX()+TextSelector.getWidth()), (int)(TextSelector.getY()+TextSelector.getHeight()));
				// Window.alert("Altura: " + He + "Ancho: " + Wi );
				recorte=!recorte;
				}
				else 
				{
					Image I = (Image) event.getSource();
					float He = I.getHeight();
					float Wi = I.getWidth();
					float Winew = (Wi / prop);
					float Henew = (He / prop);
					
					//I.setSize(Winew + "px", Henew + "px");
				}
				}
		});
	
	}
		

		
	
}
