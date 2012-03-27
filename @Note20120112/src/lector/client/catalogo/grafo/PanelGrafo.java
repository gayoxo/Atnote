package lector.client.catalogo.grafo;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.BotonesStackPanelMioGrafo;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.client.service.AnnotationSchema;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
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
	private static ClickHandler AccionAsociada;
	private static Button ButonTipo;
	private Long Catalogo;

	public PanelGrafo(Long Catalog) {

		Catalogo = Catalog;
		absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("400px", "400px");

		sPanel = new SimplePanel();
		absolutePanel.add(sPanel, 0, 0);
		sPanel.setSize("100%", "100%");
		LoadingPanel.getInstance().center();
		if (ActualUser.getLanguage() != null) {
			LoadingPanel.getInstance().setLabelTexto(
					ActualUser.getLanguage().getLoading());
		}

		bookReaderServiceHolder.getSchemaByCatalogId(Catalogo,
				new AsyncCallback<ArrayList<AnnotationSchema>>() {

					public void onSuccess(ArrayList<AnnotationSchema> result) {
						compare = result;
						LlamadaServicio();
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert("Error Retriving Catalog");
						LoadingPanel.getInstance().hide();

					}
				});

	}

	public void refresca(Long Catalog) {
		absolutePanel.clear();
		absolutePanel.add(sPanel);
		LoadingPanel.getInstance().center();
		if (ActualUser.getLanguage() != null) {
			LoadingPanel.getInstance().setLabelTexto(
					ActualUser.getLanguage().getLoading());

		}
		bookReaderServiceHolder.getSchemaByCatalogId(Catalog,
				new AsyncCallback<ArrayList<AnnotationSchema>>() {

					public void onSuccess(ArrayList<AnnotationSchema> result) {
						compare = result;
						LlamadaServicio();
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert("Error Retriving Catalog");
						LoadingPanel.getInstance().hide();

					}
				});

	}

	protected void LlamadaServicio() {
		String URLReq = generaString();
		URLReq = "https://chart.googleapis.com/chart?cht=gv:dot&chl=digraph{"
				+ URLReq + "}&chof=json";
		LoadingPanel.getInstance().center();
		if (ActualUser.getLanguage() != null) {
			LoadingPanel.getInstance().setLabelTexto(
					ActualUser.getLanguage().getLoading());
		}

		bookReaderServiceHolder.getJSONServiceTODrawGraph(URL.encode(URLReq),
				new AsyncCallback<String>() {

					public void onSuccess(String result) {
						Play(result);
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert("Errro, Try again");
						LlamadaServicio();
						LoadingPanel.getInstance().hide();

					}
				});

	}

	private String generaString() {
		StringBuffer SB = new StringBuffer();
		for (AnnotationSchema AS : compare) {
			String Pa = AS.getId().toString();
			String Name = AS.getName();
			for (Long LL : AS.getSons()) {

				for (int i = 0; i < Name.length() - Pa.length(); i++) {
					SB.append(0);
				}
				SB.append("000"); // icono
				SB.append(Pa);
				SB.append("->");
				AnnotationSchema AST = getannotationSchema(LL);
				String Pat = AST.getId().toString();
				String Namet = AST.getName();
				for (int i = 0; i < Namet.length() - Pat.length(); i++) {
					SB.append(0);
				}
				SB.append("000"); // icono
				SB.append(Pat);
				SB.append(";");
			}
		}
		return SB.toString();
	}

	private AnnotationSchema getannotationSchema(Long lL) {
		int i = 0;
		while (i < compare.size()) {
			AnnotationSchema temp = compare.get(i);
			if (temp.getId().equals(lL))
				return temp;
			i++;
		}
		return null;
	}

	private void Play(String A) {
		try {

			String[] Datosplus = A.split("\\{\"chartshape\":\\[");

			Datosplus = Datosplus[1].split("\\{");

			int lineas = 0;
			Elemento[] Flecha = new Elemento[3];

			ArrayList<Elemento> ListaElementos = new ArrayList<Elemento>();
			StringBuffer SB = new StringBuffer();
			Elemento.setLista(compare);
			Elemento.setCatalogo(Catalogo);

			for (int c = 1; c < Datosplus.length; c++) {

				Elemento E = new Elemento(Datosplus[c]);
				ListaElementos.add(E);
				SB.append(E.getCoordenadas());
				SB.append(",");
				// Window.alert(E.getName()+" " + E.getTipo());
				// Window.alert(E.getCoordenadas());

			}

			// Window.alert(SB.toString());

			Rectangulo Lienzo = GetLienzo(SB.toString());

			// Window.alert(Lienzo.getwhight() + "  " + Lienzo.getheight());
			// canvas.setSize(Integer.toString(Lienzo.getwhight())+"px",
			// Integer.toString(Lienzo.getheight())+"px");

			int W = 10 + Lienzo.getwhight();
			int H = 10 + Lienzo.getheight();
			absolutePanel.setSize(W + "px", H + "px");

			canvas = new GWTCanvas(W, H);

			canvas.setLineWidth(1);
			canvas.setStrokeStyle(Color.BLACK);

			sPanel.setWidget(canvas);

			for (Elemento E : ListaElementos) {

				if (E.getTipo() == Tipo.Type) {
					String[] coordenadas = E.getCoordenadas().split(",");
					Rectangulo But = Calculaboton(coordenadas);
					BotonesStackPanelMio sal;
					if (ButonTipo != null) {
						sal = ((BotonesStackPanelMio) ButonTipo).Clone();
						sal.setEntidad(E.getE());
					} else
						sal = new BotonesStackPanelMioGrafo("");

					sal.setHTML(E.getE().getName());

					Entity S = E.getE();
					if (S instanceof File)
						sal.setIcon("File.gif", S.getName());
					else if (S instanceof Folder)
						sal.setIcon("Folder.gif", S.getName());

					if (AccionAsociada != null)
						sal.addClickHandler(AccionAsociada);

					sal.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenter");

						}
					});

					sal.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenterPush");
						}
					});

					sal.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenter");
						}
					});

					sal.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {

							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenterOver");

						}
					});

					sal.setStyleName("gwt-ButtonCenter");

					sal.setWidth(But.getwhight() + "px");
					sal.setHeight(But.getheight() + "px");
					absolutePanel.add(sal, But.getXori(), But.getYori());
				} else {

					Flecha[lineas] = E;
					lineas++;

					if (lineas == 3) {
						lineas = 0;
						procesaFlecha(Flecha);

					}

				}
			}
		} catch (Exception e) {
			Window.alert("Error Mostrando el Grafo");
		}

	}

	private Rectangulo GetLienzo(String string) {
		String[] S = string.split(",");
		return CalculaDistancias(S);
	}

	private void procesaFlecha(Elemento[] flecha) {
		Elemento Palo = flecha[0];
		Elemento Destino = flecha[2];

		String[] coordenadasPalo = Palo.getCoordenadas().split(",");
		String[] coordenadasDestino = Destino.getCoordenadas().split(",");

		int i;
		int j;

		i = Integer.parseInt(coordenadasPalo[0]);
		j = Integer.parseInt(coordenadasPalo[1]);

		canvas.beginPath();

		canvas.moveTo(i, j);

		for (int j2 = 0; j2 < (coordenadasPalo.length / 2); j2++) {
			i = Integer.parseInt(coordenadasPalo[j2]);
			j2++;
			j = Integer.parseInt(coordenadasPalo[j2]);
			canvas.lineTo(i, j);
		}

		// Window.alert(i + " " + j);

		int tempj = j;
		int tempi = i;

		Rectangulo But = CalculaDistancias(coordenadasDestino);

		i = But.getXori() + (But.getwhight() / 2);
		j = But.getYori() + (But.getheight() / 2);

		if (j > tempj) {
			canvas.lineTo(tempi + 3, tempj);
			// Window.alert(tempi+3 + " " + j);

		} else {
			canvas.lineTo(tempi - 3, tempj);
			// Window.alert(i-3 + " " + j);

		}

		canvas.lineTo(i, j);
		tempj = j;
		tempi = i;

		i = Integer.parseInt(coordenadasPalo[coordenadasPalo.length / 2]);
		j = Integer.parseInt(coordenadasPalo[(coordenadasPalo.length / 2) + 1]);

		if (j > tempj) {
			canvas.lineTo(i + 3, j);
			// Window.alert(tempi+3 + " " + j);

		} else {
			canvas.lineTo(i - 3, j);
			// Window.alert(i-3 + " " + j);

		}

		canvas.lineTo(i, j);

		for (int j2 = (coordenadasPalo.length / 2) + 2; j2 < coordenadasPalo.length; j2++) {
			i = Integer.parseInt(coordenadasPalo[j2]);
			j2++;
			j = Integer.parseInt(coordenadasPalo[j2]);
			canvas.lineTo(i, j);
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

		i = Integer.parseInt(coordenadasDestino[0]);
		j = Integer.parseInt(coordenadasDestino[1]);

		canvas.beginPath();

		canvas.moveTo(i, j);

		i = Integer.parseInt(coordenadasDestino[2]);
		j = Integer.parseInt(coordenadasDestino[3]);

		canvas.lineTo(i, j);

		i = Integer.parseInt(coordenadasDestino[4]);
		j = Integer.parseInt(coordenadasDestino[5]);

		canvas.lineTo(i, j);

		i = Integer.parseInt(coordenadasDestino[6]);
		j = Integer.parseInt(coordenadasDestino[7]);

		canvas.lineTo(i, j);
		canvas.closePath();
		canvas.stroke();

	}

	private Rectangulo Calculaboton(String[] b) {
		Rectangulo Rec = new Rectangulo(Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MIN_VALUE, Integer.MIN_VALUE);
		for (int i = 0; i < b.length; i++) {
			int X = Integer.parseInt(b[i]);
			if (Rec.getXori() > X)
				Rec.setXori(X);
			if (Rec.getXfinal() < X)
				Rec.setXfinal(X);

			i++;
			int Y = Integer.parseInt(b[i]);
			if (Rec.getYori() > Y)
				Rec.setYori(Y);
			if (Rec.getYfinal() < Y)
				Rec.setYfinal(Y);
		}

		return Rec;
	}

	public static void setAccionAsociada(ClickHandler accionAsociada) {
		AccionAsociada = accionAsociada;
	}

	public static ClickHandler getAccionAsociada() {
		return AccionAsociada;
	}

	public static void setBotonTipo(BotonesStackPanelMio buttonMio) {
		ButonTipo = buttonMio;

	}
}
