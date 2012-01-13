package lector.client.reader.filter;



import lector.client.catalogo.BotonesStackPanelMio;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BotonesStackPanelfilterMio extends BotonesStackPanelMio{


	private VerticalPanel Selected;
	private VerticalPanel Normal;
	
	public BotonesStackPanelfilterMio(String HTML,VerticalPanel Normal, VerticalPanel Selected) {
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
		BotonesStackPanelfilterMio New=new BotonesStackPanelfilterMio(getHTML(), Normal, Selected);	
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
		for (int i = 0; i < Selected.getWidgetCount(); i++) {
			BotonesStackPanelfilterMio BSM= (BotonesStackPanelfilterMio)Selected.getWidget(i);
			if (BSM.getEntidad().getID().intValue()==super.getEntidad().getID().intValue()) return true;
		}
		return false;
	}
}
