package lector.client.catalogo.grafo;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.service.AnnotationSchema;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class PanelGrafo extends Composite {

	private GWTCanvas canvas;
	private AbsolutePanel absolutePanel;
	private SimplePanel sPanel;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ArrayList<AnnotationSchema> compare;
	
	public PanelGrafo(Long Catalog) {
		
		absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("400px", "400px");
		
		sPanel = new SimplePanel();
		absolutePanel.add(sPanel, 0, 0);
		sPanel.setSize("100%", "100%");
		
		bookReaderServiceHolder.getSchemaByCatalogId(Catalog, new AsyncCallback<ArrayList<AnnotationSchema>>() {
			
			public void onSuccess(ArrayList<AnnotationSchema> result) {
				compare=result;
				LlamadaServicio();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error Retriving Catalog");
				
			}
		});
		
	}

	
	protected void LlamadaServicio() {
		String URLReq=generaString();
		URLReq="https://chart.googleapis.com/chart?cht=gv:dot&chl=digraph{"+URLReq+"}&chof=json";
		bookReaderServiceHolder.getJSONServiceTODrawGraph(URL.encode(URLReq), new AsyncCallback<String>() {
			
			public void onSuccess(String result) {
				Play(result);
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Errro, Try again");
				LlamadaServicio();
				
			}
		});
		
	}


	private String generaString() {
		StringBuffer SB=new StringBuffer();
		for (AnnotationSchema AS : compare) {
			Long Pa=AS.getId();
			for (Long LL : AS.getSons()) {
				SB.append(Pa);
				SB.append("->");
				SB.append(LL);
				SB.append(";");
			}
		}
		return SB.toString();
	}


	private void Play(String A)
	{
		try {
			
			String[] Datosplus=A.split("\\{\"chartshape\":\\[");
			
			Datosplus=Datosplus[1].split("\\{");
			
			int lineas=0;
			Elemento[] Flecha= new Elemento[3];
			
			ArrayList<Elemento> ListaElementos=new ArrayList<Elemento>();
			StringBuffer SB=new StringBuffer();
			
			for (int c = 1; c < Datosplus.length; c++) {
				
				Elemento E = new Elemento(Datosplus[c]);
				ListaElementos.add(E);
				SB.append(E.getCoordenadas());
				SB.append(",");
				//Window.alert(E.getName()+" " + E.getTipo());
				//Window.alert(E.getCoordenadas());
				
			}
			
		//	Window.alert(SB.toString());
			
			Rectangulo Lienzo=GetLienzo(SB.toString());
			
		//	Window.alert(Lienzo.getwhight() + "  " + Lienzo.getheight());
			//canvas.setSize(Integer.toString(Lienzo.getwhight())+"px", Integer.toString(Lienzo.getheight())+"px");
			
			int W=10+Lienzo.getwhight();
			int H=10+Lienzo.getheight();
			absolutePanel.setSize(W+"px",H+"px");
			
			canvas = new GWTCanvas(W,H);
		    
		    canvas.setLineWidth(1);
		    canvas.setStrokeStyle(Color.BLACK);
		    
		    sPanel.setWidget(canvas);
		    
			
			for (Elemento E:ListaElementos){
				
				if (E.getTipo()==Tipo.Type)
				{
					String[] coordenadas=E.getCoordenadas().split(",");
					Rectangulo But=Calculaboton(coordenadas);
				    Button sal=new Button(E.getName());
				    sal.setWidth(But.getwhight()+"px");
				    sal.setHeight(But.getheight()+"px");
				    absolutePanel.add(sal,But.getXori(),But.getYori());
				}
				else 
				{

			    	

			    	Flecha[lineas]=E;
			    	lineas++;
			    	
			    	if (lineas==3)
			    	{
			    		lineas=0;
			    		procesaFlecha(Flecha);
			    		
			    	}	

			    	
			     
				}
			}
		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
		
	}

	private Rectangulo GetLienzo(String string) {
		String[] S=string.split(",");
		return CalculaDistancias(S);
	}

	private void procesaFlecha(Elemento[] flecha) {
		Elemento Palo= flecha[0];
		Elemento Destino=flecha[2];
		
		String[] coordenadasPalo=Palo.getCoordenadas().split(",");
		String[] coordenadasDestino=Destino.getCoordenadas().split(",");
		
		int i;
		int j;
		
		i=Integer.parseInt(coordenadasPalo[0]);
		j=Integer.parseInt(coordenadasPalo[1]);
		
		canvas.beginPath();
    	
    	canvas.moveTo(i,j);
    	
    	for (int j2 = 0; j2 < (coordenadasPalo.length/2); j2++) {
    		i=Integer.parseInt(coordenadasPalo[j2]);
    		j2++;
        	j=Integer.parseInt(coordenadasPalo[j2]);        	
        	canvas.lineTo(i,j);
		}
    	
//    	Window.alert(i + " " + j);
    	
    	  	
    	int tempj=j;
    	int tempi=i;
    	
    	Rectangulo But=CalculaDistancias(coordenadasDestino);

    	i=But.getXori()+(But.getwhight()/2);
    	j=But.getYori()+(But.getheight()/2);
    	
    	if (j>tempj)
    	{
    	canvas.lineTo(tempi+3,tempj);
//    	Window.alert(tempi+3 + " " + j);

    	}else 
    	{
    		canvas.lineTo(tempi-3,tempj);
//        	Window.alert(i-3 + " " + j);
        	
    	}
      	
    	canvas.lineTo(i,j);
    	tempj=j;
    	tempi=i;
    	
    	i=Integer.parseInt(coordenadasPalo[coordenadasPalo.length/2]);
    	j=Integer.parseInt(coordenadasPalo[(coordenadasPalo.length/2)+1]);  
    	

    	if (j>tempj)
    	{
    	canvas.lineTo(i+3,j);
//    	Window.alert(tempi+3 + " " + j);

    	}else 
    	{
    		canvas.lineTo(i-3,j);
//        	Window.alert(i-3 + " " + j);
        	
    	}
    	
    	canvas.lineTo(i,j);
    	
    	
    	for (int j2 = (coordenadasPalo.length/2)+2 ; j2 < coordenadasPalo.length; j2++) {
    		i=Integer.parseInt(coordenadasPalo[j2]);
    		j2++;
        	j=Integer.parseInt(coordenadasPalo[j2]);        	
        	canvas.lineTo(i,j);
		}
    	
    	canvas.closePath();
	    canvas.stroke();
	    
	   // paintDestino(coordenadasDestino);
		
	}

	private Rectangulo CalculaDistancias(String[] coordenadasDestino) {
		
		return Calculaboton(coordenadasDestino);
	}

	public void paintDestino(String[] coordenadasDestino) {
		int i;
		int j;
		
		i=Integer.parseInt(coordenadasDestino[0]);
		j=Integer.parseInt(coordenadasDestino[1]);
		
		canvas.beginPath();
    	
    	canvas.moveTo(i,j);
    	
    	i=Integer.parseInt(coordenadasDestino[2]);
    	j=Integer.parseInt(coordenadasDestino[3]);
    	

    	canvas.lineTo(i,j);

    	i=Integer.parseInt(coordenadasDestino[4]);
    	j=Integer.parseInt(coordenadasDestino[5]);
    	
    	
    	canvas.lineTo(i,j);
    	
    	i=Integer.parseInt(coordenadasDestino[6]);
    	j=Integer.parseInt(coordenadasDestino[7]);
    	
    	canvas.lineTo(i,j);
    	canvas.closePath();
	    canvas.stroke();
		
	}
	
	private Rectangulo Calculaboton(String[] b) {
		Rectangulo Rec=new Rectangulo(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
		for (int i = 0; i < b.length; i++) {
			int X=Integer.parseInt(b[i]);
			if (Rec.getXori()>X)
				Rec.setXori(X);
			if (Rec.getXfinal()<X)
				Rec.setXfinal(X);
			
			i++;
			int Y=Integer.parseInt(b[i]);
			if (Rec.getYori()>Y)
				Rec.setYori(Y);
			if (Rec.getYfinal()<Y)
				Rec.setYfinal(Y);
		}

		return Rec;
	}
}
