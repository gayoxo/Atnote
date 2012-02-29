package lector.client.admin;



import lector.client.catalogo.BotonesStackPanelMio;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BotonesStackPanelAdministracionMio extends BotonesStackPanelMio{

	private VerticalPanel Selected;
	private VerticalPanel Normal;
	
	public BotonesStackPanelAdministracionMio(String HTML,VerticalPanel Normal, VerticalPanel Selected) {
		super(HTML);
		this.Normal=Normal;
		this.Selected=Selected;
		this.Actual=Normal;
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
			Actual.add(this);
	}
	
	
	

	public BotonesStackPanelMio Clone()
	{
	BotonesStackPanelAdministracionMio New=new BotonesStackPanelAdministracionMio(getHTML(), Normal, Selected);	
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

	private boolean EstainSelected() {
		for (int i = 1; i < Selected.getWidgetCount(); i++) {
			BotonesStackPanelAdministracionMio BSM= (BotonesStackPanelAdministracionMio)Selected.getWidget(i);
			if (BSM.getEntidad().getID().intValue()==super.getEntidad().getID().intValue()) return true;
			if (!BSM.getEntidad().getActualFather().equals(super.getEntidad().getActualFather())) return true;
		}
		return false;
	}


}
