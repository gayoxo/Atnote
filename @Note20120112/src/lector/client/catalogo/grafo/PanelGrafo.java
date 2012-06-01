package lector.client.catalogo.grafo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.BotonesStackPanelMioGrafo;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.ErrorConstants;
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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

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
	private VerticalPanel zoomPanel;
	private Button btnNewButton;
	private Button btnNewButton_1;
	private static float multiplicador;
	private String Result;
	private Button btnNewButton_2;
	private HorizontalPanel horizontalPanel;
	private static int ContadorErrores;
	private static TipeGraph Visualize;
	private VerticalPanel verticalPanel;
	private Button dot;
	private HorizontalPanel horizontalPanel_1;
	private Button neato;
	private Button twopi;
	private Button circo;
	private Button fdp;
	
	
	
	public PanelGrafo(Long Catalog) {

		Catalogo = Catalog;
		if (multiplicador<=0.0f) multiplicador=1.0f;
		Result=null;
		Visualize=TipeGraph.dot;
		
		horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);

		zoomPanel = new VerticalPanel();
		horizontalPanel.add(zoomPanel);

		btnNewButton = new Button("Mas");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				multiplicador=multiplicador+0.1f;
				Play() ;
//				LlamadaServicio(
////						(int) Math.round(OldSizehight * 1.3),
////						(int) Math.round(OldSizewigth * 1.3), true
//						);

			}

		});
		btnNewButton.setText("+");
		btnNewButton.setHTML("<img src=\"ZPlus.gif\">");
		zoomPanel.add(btnNewButton);
		zoomPanel.setCellVerticalAlignment(btnNewButton,
				HasVerticalAlignment.ALIGN_MIDDLE);
		zoomPanel.setCellHorizontalAlignment(btnNewButton,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnNewButton_1 = new Button("Menos");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				if (multiplicador-0.1f>0.2f) multiplicador=multiplicador-0.1f;
				Play() ;
//				LlamadaServicio(
////						(int) Math.round(OldSizehight / 1.3),
////						(int) Math.round(OldSizewigth / 1.3), true
//						);
			}
		});
		btnNewButton_1.setText("-");
		btnNewButton_1.setHTML("<img src=\"ZLess.gif\">");
		zoomPanel.add(btnNewButton_1);

		zoomPanel.setCellVerticalAlignment(btnNewButton_1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		zoomPanel.setCellHorizontalAlignment(btnNewButton_1,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnNewButton_2 = new Button("Reset");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				multiplicador=1.0f;
				Play();
//				LlamadaServicio(
////						0, 0, false
//						);
			}
		});
		btnNewButton_2.setText("Reset");
		btnNewButton_2.setHTML("<img src=\"Z.gif\">");

		btnNewButton.setEnabled(false);
		btnNewButton_1.setEnabled(false);
		btnNewButton_2.setEnabled(false);

		zoomPanel.add(btnNewButton_2);
		zoomPanel.setCellVerticalAlignment(btnNewButton_2,
				HasVerticalAlignment.ALIGN_MIDDLE);
		zoomPanel.setCellHorizontalAlignment(btnNewButton_2,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		
		horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		dot = new Button("dot");
		dot.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Visualize=TipeGraph.dot;
				DisableStyles();
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				LlamadaServicio();
			}
		});
		
		dot.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");

			}
		});

		dot.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphPush");
			}
		});

		dot.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");
			}
		});

		dot.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphOver");

			}
		});

		dot.setStyleName("gwt-ButtonCenterGraph");
		
		horizontalPanel_1.add(dot);
		
		neato = new Button("neato");
		neato.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Visualize=TipeGraph.neato;
				DisableStyles();
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				LlamadaServicio();
			}
		});
		horizontalPanel_1.add(neato);
		
		neato.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");

			}
		});

		neato.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphPush");
			}
		});

		neato.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");
			}
		});

		neato.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphOver");

			}
		});

		neato.setStyleName("gwt-ButtonCenterGraph");
		
		
		twopi = new Button("twopi");
		twopi.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Visualize=TipeGraph.twopi;
				DisableStyles();
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				LlamadaServicio();
			}
		});
		horizontalPanel_1.add(twopi);
		
		twopi.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");

			}
		});

		twopi.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphPush");
			}
		});

		twopi.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");
			}
		});

		twopi.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphOver");

			}
		});

		twopi.setStyleName("gwt-ButtonCenterGraph");
		
		
		circo = new Button("circo");
		circo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Visualize=TipeGraph.circo;
				DisableStyles();
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				LlamadaServicio();
			}
		});
		horizontalPanel_1.add(circo);
		
		circo.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");

			}
		});

		circo.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphPush");
			}
		});

		circo.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");
			}
		});

		circo.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphOver");

			}
		});

		circo.setStyleName("gwt-ButtonCenterGraph");
		
		
		fdp = new Button("fdp");
		fdp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Visualize=TipeGraph.fdp;
				DisableStyles();
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				absolutePanel.clear();
				absolutePanel.add(sPanel, 0, 0);
				LlamadaServicio();
			}
		});
		horizontalPanel_1.add(fdp);
		
		fdp.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");

			}
		});

		fdp.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphPush");
			}
		});

		fdp.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraph");
			}
		});

		fdp.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterGraphOver");

			}
		});

		fdp.setStyleName("gwt-ButtonCenterGraph");

		DisableStyles();
		absolutePanel = new AbsolutePanel();
		verticalPanel.add(absolutePanel);
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
						ContadorErrores=0;
						LlamadaServicio(
//								0, 0, false
								);
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert("Error Retriving Catalog");
						LoadingPanel.getInstance().hide();

					}
				});

	}

	protected void DisableStyles() {
		dot.setVisible(false);
		neato.setVisible(false);
		twopi.setVisible(false);
		circo.setVisible(false);
		fdp.setVisible(false);		
	}

	private void EnableStyles() {
		dot.setVisible(true);
		neato.setVisible(true);
		twopi.setVisible(true);
		circo.setVisible(true);
		fdp.setVisible(true);
	}
	
	public void refresca(Long Catalog) {
		absolutePanel.clear();
		absolutePanel.add(sPanel, 0, 0);
		LoadingPanel.getInstance().center();
		if (ActualUser.getLanguage() != null) {
			LoadingPanel.getInstance().setLabelTexto(
					ActualUser.getLanguage().getLoading());

		}
		bookReaderServiceHolder.getSchemaByCatalogId(Catalog,
				new AsyncCallback<ArrayList<AnnotationSchema>>() {

					public void onSuccess(ArrayList<AnnotationSchema> result) {
						compare = result;
						ContadorErrores=0;
						LlamadaServicio(
//								0, 0, false
								);
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert("Error Retriving Catalog");
						LoadingPanel.getInstance().hide();

					}
				});

	}

	protected void LlamadaServicio(
//			int newSizehight2, int newSizewigth2,boolean sizeset
			) {
		String URLReq = generaString();

//		if ((newSizehight2 * newSizewigth2) > 300000) {
//			float prop = ((float)300000)/((float)newSizehight2 * (float)newSizewigth2);
//			prop=(float) Math.sqrt(prop);
////			BigDecimal bd = new BigDecimal((float)newSizehight2*prop);
////			BigDecimal positiveRedondeado = bd.setScale(0, RoundingMode.DOWN);	
////			newSizehight2 = positiveRedondeado.intValue();
////			bd = new BigDecimal((float)newSizewigth2*prop);
////			positiveRedondeado = bd.setScale(0, RoundingMode.DOWN);	
////			newSizewigth2 = positiveRedondeado.intValue();
//			newSizehight2 = (int) Math.round(((float)newSizehight2) * prop);
//			newSizewigth2 = (int) Math.round(((float)newSizewigth2) * prop);
//		}
//		if (newSizehight2 > 1000) {
//			newSizewigth2 = (1000 * newSizewigth2) / newSizehight2;
//			newSizehight2 = 1000;
//		}
//		if (newSizewigth2 > 1000) {
//			newSizehight2 = (1000 * newSizehight2) / newSizewigth2;
//			newSizewigth2 = 1000;
//		}
		if (!URLReq.isEmpty()) {
//			if (!sizeset)	
			URLReq = "cht=gv:"+Visualize+"&chl=digraph{"
						+ URLReq + "}&chof=json";
//			else
//				URLReq = "https://chart.googleapis.com/chart?cht=gv:dot&chl=digraph{"
//						+ URLReq
//						+ "}&chof=json&chs="
//						+ newSizewigth2
//						+ "x"
//						+ newSizehight2;
			LoadingPanel.getInstance().center();
			if (ActualUser.getLanguage() != null) {
				LoadingPanel.getInstance().setLabelTexto(
						ActualUser.getLanguage().getLoading());
			}

			bookReaderServiceHolder.getJSONServiceTODrawGraph(URL.encode("https://chart.googleapis.com/chart?"),
					URL.encode(URLReq), new AsyncCallback<String>() {

						public void onSuccess(String result) {
							Result=result;
							Play();
							LoadingPanel.getInstance().hide();

						}

						public void onFailure(Throwable caught) {
							Window.alert("Errro, Try again");
							ContadorErrores++;
							if (ContadorErrores!=4) 
								LlamadaServicio(
//									0, 0, false
									);
							else Window.alert(ErrorConstants.ERROR_LOAD_IMAGE);
							LoadingPanel.getInstance().hide();

						}
					});
		}
	}

	private String generaString() {
		StringBuffer SB = new StringBuffer();
		for (AnnotationSchema AS : compare) {
			String Pa = AS.getId().toString();
			String Name = AS.getName();
			for (Long LL : AS.getSons()) {
				
				AnnotationSchema AST = getannotationSchema(LL);
				for (int i = 0; i < Name.length() - Pa.length(); i++) {
					SB.append(0);
				}
				SB.append("000"); // icono
				SB.append(Pa);
				SB.append("->");
				
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

	private void Play() {
		try {

			String[] Datosplus = Result.split("\\{\"chartshape\":\\[");

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

			float FW=(float)Lienzo.getwhight()*multiplicador;
			float FH=(float)Lienzo.getheight()*multiplicador;
			
			int W = 10 + Math.round(FW);
			int H = 10 + Math.round(FH);

			btnNewButton.setEnabled(true);
			btnNewButton_1.setEnabled(true);
			btnNewButton_2.setEnabled(true);
			EnableStyles();

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
					if (multiplicador>0.6f){
					if (S instanceof File)
						sal.setIcon("File.gif", S.getName());
					else if (S instanceof Folder)
						sal.setIcon("Folder.gif", S.getName());
					}
					if (AccionAsociada != null)
						sal.addClickHandler(AccionAsociada);

					sal.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenterGraph");

						}
					});

					sal.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenterGraphPush");
						}
					});

					sal.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenterGraph");
						}
					});

					sal.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {

							((Button) event.getSource())
									.setStyleName("gwt-ButtonCenterGraphOver");

						}
					});

					sal.setStyleName("gwt-ButtonCenterGraph");

					float WF=(float)But.getwhight()*multiplicador;
					float HF=(float)But.getheight()*multiplicador;
					sal.setWidth(Math.round(WF) + "px");
					sal.setHeight(Math.round(HF) + "px");
					float XF=(float)But.getXori()*multiplicador;
					float YF=(float)But.getYori()*multiplicador;
					absolutePanel.add(sal, Math.round(XF), Math.round(YF));
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
			LlamadaServicio(
//					0, 0, false
					);
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
		

		float iF=(float)i*multiplicador;
		float jF=(float)j*multiplicador;
		
		i=Math.round(iF);
		j=Math.round(jF);
		
		canvas.beginPath();

		canvas.moveTo(i, j);

		for (int j2 = 0; j2 < (coordenadasPalo.length / 2); j2++) {
			i = Integer.parseInt(coordenadasPalo[j2]);
			j2++;
			j = Integer.parseInt(coordenadasPalo[j2]);
			
			iF=(float)i*multiplicador;
			jF=(float)j*multiplicador;
			
			i=Math.round(iF);
			j=Math.round(jF);
			
			canvas.lineTo(i, j);
		}

		// Window.alert(i + " " + j);

		int tempj = j;
		int tempi = i;

		Rectangulo But = CalculaDistancias(coordenadasDestino);

		float WF=(float)But.getwhight()*multiplicador;
		float HF=(float)But.getheight()*multiplicador;
		float XF=(float)But.getXori()*multiplicador;
		float YF=(float)But.getYori()*multiplicador;
		
		
		i = Math.round(XF) + (Math.round(WF) / 2);
		j = Math.round(YF) + (Math.round(HF) / 2);

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

		iF=(float)i*multiplicador;
		jF=(float)j*multiplicador;
		
		i=Math.round(iF);
		j=Math.round(jF);
		
		
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
			
			iF=(float)i*multiplicador;
			jF=(float)j*multiplicador;
			
			i=Math.round(iF);
			j=Math.round(jF);
			
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

		float iF = (float)i*multiplicador;
		float jF = (float)j*multiplicador;
		
		i=Math.round(iF);
		j=Math.round(jF);
		
		canvas.beginPath();

		canvas.moveTo(i, j);

		i = Integer.parseInt(coordenadasDestino[2]);
		j = Integer.parseInt(coordenadasDestino[3]);
		
		iF=(float)i*multiplicador;
		jF=(float)j*multiplicador;
		
		i=Math.round(iF);
		j=Math.round(jF);
		

		canvas.lineTo(i, j);

		i = Integer.parseInt(coordenadasDestino[4]);
		j = Integer.parseInt(coordenadasDestino[5]);
		
		iF=(float)i*multiplicador;
		jF=(float)j*multiplicador;
		
		i=Math.round(iF);
		j=Math.round(jF);
		

		canvas.lineTo(i, j);

		i = Integer.parseInt(coordenadasDestino[6]);
		j = Integer.parseInt(coordenadasDestino[7]);
		
		iF=(float)i*multiplicador;
		jF=(float)j*multiplicador;
		
		i=Math.round(iF);
		j=Math.round(jF);

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
