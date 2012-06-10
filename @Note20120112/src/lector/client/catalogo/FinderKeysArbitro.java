package lector.client.catalogo;

import java.util.ArrayList;
import java.util.Stack;

public class FinderKeysArbitro {


	
	private ArrayList<ElementKey> StackPanelEle;
	private ArrayList<ElementKey> Padres;
	private static FinderKeysArbitro YO;
	private static boolean estado;

	private FinderKeysArbitro() {
		StackPanelEle=new ArrayList<ElementKey>();
		Padres=new ArrayList<ElementKey>();
		estado=false;
	}
	
	
	public void add(ElementKey a) {
		StackPanelEle.add(a);
		if (!estado)
			{
				ElementKey E = StackPanelEle.get(0);
				StackPanelEle.remove(0);
				E.getActulizador().click();
				estado=true;
			}
		
		
	}

	public void setfalse()
	{
		if (!StackPanelEle.isEmpty())
			{
			ElementKey E = StackPanelEle.get(0);
			StackPanelEle.remove(0);
			E.getActulizador().click();
			}
		else estado=false;
	}
	
	public static FinderKeysArbitro getInstance()
	{
		if (YO==null) YO=new FinderKeysArbitro();
		
		return YO;
	}
	
	public ElementKey getPadre()
	{
		ElementKey E= Padres.get(0);
		Padres.remove(0);
		return E;
	}


	public void addfather(ElementKey eK) {
		Padres.add(eK);
		
	}
	
	public void refresh()
	{
		StackPanelEle=new ArrayList<ElementKey>();
		Padres=new ArrayList<ElementKey>();
		estado=false;
	}
}
