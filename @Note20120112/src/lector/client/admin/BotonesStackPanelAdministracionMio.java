package lector.client.admin;



import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.VerticalPanel;

public class BotonesStackPanelAdministracionMio extends BotonesStackPanelMio{

	protected VerticalPanel Selected;
	protected VerticalPanel Normal;
	private Finder F;
	
	public BotonesStackPanelAdministracionMio(String HTML,VerticalPanel Normal, VerticalPanel Selected, Finder Fin) {
		super(HTML);
		this.Normal=Normal;
		this.Selected=Selected;
		this.Actual=Normal;
		F=Fin;
		Actual.add(this);
	}
	

	public void Swap() {
		Actual.remove(this);
		if (Actual==Normal)
			{
			Actual=Selected;
			}
		else 
			{
			Actual=Normal;
			}
		if((this.getEntidad()!=null)&&!EstainSelected())
			if (Actual==Normal)
			{
				if (checkFamilia())
					Actual.add(this);
			}
			else Actual.add(this);
	}
	
	
	

	private boolean checkFamilia() {
		if (F==null) return true;
		return F.getTopPath().equals(super.getEntidad().getActualFather());
	}

	public BotonesStackPanelMio Clone()
	{
	BotonesStackPanelAdministracionMio New=new BotonesStackPanelAdministracionMio(getHTML(), Normal, Selected,F);	
	New.setActual(getActual());
	return New;
	}
	
	public VerticalPanel getSelected() {
		return Selected;
	}
	
	public void setSelected(VerticalPanel selected) {
		Selected = selected;
	}
	public void setNormal(VerticalPanel normal) {
		Normal = normal;
	}
	
	public VerticalPanel getNormal() {
		return Normal;
	}

	@Override
	public void setActual(VerticalPanel actual) {
		Normal=actual;
		if (Actual!=null)Actual.remove(this);
		Actual = actual;
		if((this.getEntidad()!=null)&&!EstainSelected())
			Actual.add(this);
	
	}

	protected boolean EstainSelected() {
		for (int i = 1; i < Selected.getWidgetCount(); i++) {
			BotonesStackPanelAdministracionMio BSM= (BotonesStackPanelAdministracionMio)Selected.getWidget(i);
			if (BSM.getEntidad().getID().intValue()==super.getEntidad().getID().intValue()) return true;
		}
		return false;
	}

	public Finder getF() {
		return F;
	}

}
