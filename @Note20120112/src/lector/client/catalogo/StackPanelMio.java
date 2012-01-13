package lector.client.catalogo;


import lector.client.admin.admins.EntidadAdmin;
import lector.client.admin.book.EntidadLibro;
import lector.client.admin.users.EntidadUser;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.client.TagEntity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class StackPanelMio extends StackPanel {
	
	VerticalPanel A;
	VerticalPanel Numbers;
    VerticalPanel B;
    VerticalPanel C;
    VerticalPanel D;
    VerticalPanel E;
    VerticalPanel F;
    VerticalPanel G;
    VerticalPanel H;
    VerticalPanel I;
    VerticalPanel J;
    VerticalPanel K;
    VerticalPanel L;
    VerticalPanel M;
    VerticalPanel N;
    VerticalPanel O;
    VerticalPanel P;
    VerticalPanel Q;
    VerticalPanel R;
    VerticalPanel S;
    VerticalPanel T;
    VerticalPanel U;
    VerticalPanel W;
    VerticalPanel X;
    VerticalPanel Y;
    VerticalPanel Z;
    VerticalPanel Others;
    VerticalPanel ALL;
    ClickHandler BotonClick;
    BotonesStackPanelMio BotonTipo;
    
	public StackPanelMio() {
		
		BotonClick =new ClickHandler() {

	        public void onClick(ClickEvent event) {
	        	Window.alert("Not Seted");
	        }
	        };
		
		A = new VerticalPanel();
		
        Numbers = new VerticalPanel();
	//	add(Numbers, "#", false);
		Numbers.setSize("100%", "100%");
        
	//	stackPanel_1.add(A, "A", false);
        A.setSize("100%", "100%");
        A.setVisible(false);
        B = new VerticalPanel();
   //     stackPanel_1.add(B, "B ", false);
        B.setSize("100%", "100%");
        
         C = new VerticalPanel();
     //   stackPanel_1.add(C, "C", false);
        C.setSize("100%", "100%");
        
         D = new VerticalPanel();
   //     stackPanel_1.add(D, "D", false);
        D.setSize("100%", "100%");
        
         E = new VerticalPanel();
    //    stackPanel_1.add(E, "E", false);
        E.setSize("100%", "100%");
        
         F = new VerticalPanel();
     //   stackPanel_1.add(F, "F", false);
        F.setSize("100%", "100%");
        
         G = new VerticalPanel();
    //    stackPanel_1.add(G, "G", false);
        G.setSize("100%", "100%");
        
         H = new VerticalPanel();
   //     stackPanel_1.add(H, "H", false);
        H.setSize("100%", "100%");
        
         I = new VerticalPanel();
   //    stackPanel_1.add(I, "I", false);
        I.setSize("100%", "100%");
        
         J = new VerticalPanel();
   //     stackPanel_1.add(J, "J", false);
        J.setSize("100%", "100%");
        
         K = new VerticalPanel();
   //     stackPanel_1.add(K, "K", false);
        K.setSize("100%", "100%");
        
         L = new VerticalPanel();
  //      stackPanel_1.add(L, "L", false);
        L.setSize("100%", "100%");
        
         M = new VerticalPanel();
  //      stackPanel_1.add(M, "M", false);
        M.setSize("100%", "100%");
        
         N = new VerticalPanel();
   //     stackPanel_1.add(N, "N", false);
        N.setSize("100%", "100%");
        
         O = new VerticalPanel();
  //      stackPanel_1.add(O, "O", false);
        O.setSize("100%", "100%");
        
         P = new VerticalPanel();
   //     stackPanel_1.add(P, "P", false);
        P.setSize("100%", "100%");
        
         Q = new VerticalPanel();
   //     stackPanel_1.add(Q, "Q", false);
        Q.setSize("100%", "100%");
        
         R = new VerticalPanel();
  //      add(R, "R", false);
        R.setSize("100%", "100%");
        
        S = new VerticalPanel();
 //       add(S, "S", false);
        S.setSize("100%", "100%");
        
         T = new VerticalPanel();
  //      add(T, "T", false);
        T.setSize("100%", "100%");
        
         U = new VerticalPanel();
  //      add(U, "U", false);
        U.setSize("100%", "100%");
        
         W = new VerticalPanel();
   //     add(W, "W", false);
        W.setSize("100%", "100%");
        
         X = new VerticalPanel();
   //     add(X, "X", false);
        X.setSize("100%", "100%");
        
         Y = new VerticalPanel();
    //    add(Y, "Y", false);
        Y.setSize("100%", "100%");
        
         Z = new VerticalPanel();
   //     add(Z, "Z", false);
        Z.setSize("100%", "100%");
        
        Others = new VerticalPanel();
     //   add(Others, "Symbols", false);
        Others.setSize("100%", "100%");
        
        ALL = new VerticalPanel();
     //   add(ALL, "All", false);
        ALL.setSize("100%", "100%");
	}

	public void Clear() {
//		for (int i = 0; i < getWidgetCount(); i++) {
//			((VerticalPanel)getWidget(i)).clear();
//		}	
		Numbers.clear();
		A.clear();    
		B.clear();
		C.clear();
		D.clear(); 
		E.clear(); 
		F.clear(); 
		G.clear(); 
		H.clear(); 
		I.clear(); 
		J.clear(); 
		K.clear(); 
		L.clear(); 
		M.clear(); 
		N.clear(); 
		O.clear(); 
		P.clear(); 
		Q.clear(); 
		R.clear(); 
		S.clear(); 
		T.clear(); 
		U.clear(); 
		W.clear(); 
		X.clear(); 
		Y.clear(); 
		Z.clear(); 
		Others.clear(); 
		ALL.clear(); 
		
	}
	
	public void ClearEmpty() {
		clear();
		if (Numbers.getWidgetCount()!=0) add(Numbers, "#", false);        
		if (A.getWidgetCount()!=0) add(A, "A", false);    
		if (B.getWidgetCount()!=0) add(B, "B ", false);
		if (C.getWidgetCount()!=0) add(C, "C", false);
		if (D.getWidgetCount()!=0)add(D, "D", false);
		if (E.getWidgetCount()!=0)add(E, "E", false);
		if (F.getWidgetCount()!=0)add(F, "F", false);
		if (G.getWidgetCount()!=0)add(G, "G", false);
		if (H.getWidgetCount()!=0)add(H, "H", false);
		if (I.getWidgetCount()!=0)add(I, "I", false);
		if (J.getWidgetCount()!=0)add(J, "J", false);
		if (K.getWidgetCount()!=0)add(K, "K", false);
		if (L.getWidgetCount()!=0)add(L, "L", false);
		if (M.getWidgetCount()!=0)add(M, "M", false);
		if (N.getWidgetCount()!=0)add(N, "N", false);
		if (O.getWidgetCount()!=0)add(O, "O", false);
		if (P.getWidgetCount()!=0)add(P, "P", false);
		if (Q.getWidgetCount()!=0)add(Q, "Q", false);
		if (R.getWidgetCount()!=0)add(R, "R", false);
		if (S.getWidgetCount()!=0)add(S, "S", false);
		if (T.getWidgetCount()!=0)add(T, "T", false);
		if (U.getWidgetCount()!=0)add(U, "U", false);
		if (W.getWidgetCount()!=0)add(W, "W", false);
		if (X.getWidgetCount()!=0)add(X, "X", false);
		if (Y.getWidgetCount()!=0)add(Y, "Y", false);
		if (Z.getWidgetCount()!=0)add(Z, "Z", false);
		if (Others.getWidgetCount()!=0)add(Others, "Symbols", false);
		if (ALL.getWidgetCount()!=0)add(ALL, "ALL", false);
        
	}
	
	public void addBoton(Entity S) {
		if (S.getName().isEmpty()) Window.alert("el string jamas deberia ser vacio");
		VerticalPanel Actual;
		char Orden=S.getName().charAt(0);
			switch (Orden) {
			case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':case '0':
				Actual=Numbers;
				break;
			case 'A':case 'a':
				Actual=A;
				break;
			case 'B':case 'b':
				Actual=B;
				break;
			case 'C':case 'c':
				Actual=C;
				break;
			case 'D':case 'd':
				Actual=D;
				break;
			case 'E':case 'e':
				Actual=E;
				break;
			case 'F':case 'f':
				Actual=F;
				break;
			case 'G':case 'g':
				Actual=G;
				break;
			case 'H':case 'h':
				Actual=H;
				break;
			case 'I':case 'i':
				Actual=I;
				break;
			case 'J':case 'j':
				Actual=J;
				break;
			case 'K':case 'k':
				Actual=K;
				break;
			case 'L':case 'l':
				Actual=L;
				break;
			case 'M':case 'm':
				Actual=M;
				break;
			case'N':case 'n':
				Actual=N;
				break;
			case 'O':case 'o':
				Actual=O;
				break;
			case 'P':case 'p':
				Actual=P;
				break;
			case 'Q':case 'q':
				Actual=Q;
				break;
			case 'R':case 'r':
				Actual=R;
				break;			
			case 'S':case 's':
				Actual=this.S;
				break;
			case 'T':case 't':
				Actual=T;
				break;
			case 'U':case 'u':
				Actual=U;
				break;
			case 'W':case 'w':
				Actual=W;
				break;
			case 'X':case 'x':
				Actual=X;
				break;
			case 'Y':case 'y':
				Actual=Y;
				break;
			case 'Z':case 'z':
				Actual=Z;
				break;
			default:
				Actual=Others;
				break;
			}
			
			BotonesStackPanelMio Act = BotonTipo.Clone();
			Act.setEntidad(S);
			Act.setHTML(S.getName());
			
			if (S instanceof File) Act.setIcon("File.gif",S.getName());
			else if (S instanceof Folder)Act.setIcon("Folder.gif",S.getName());
			else if (S instanceof EntidadLibro) Act.setIcon("Book.gif",S.getName());
			else if (S instanceof TagEntity) Act.setIcon("File2.gif",S.getName());
			else if (S instanceof EntidadUser) Act.setIcon("Users.gif",S.getName());
			else if (S instanceof EntidadAdmin) Act.setIcon("Admin.gif",S.getName());
			
			Act.setActual(Actual);
			Act.addClickHandler(BotonClick);
	}
	
	public void addBotonLessTen(Entity S) {
		if (S.getName().isEmpty()) Window.alert("el string jamas deberia ser vacio");
		BotonesStackPanelMio Act = BotonTipo.Clone();
		Act.setEntidad(S);
		Act.setHTML(S.getName());
		
		if (S instanceof File) Act.setIcon("File.gif",S.getName());
		else if (S instanceof Folder)Act.setIcon("Folder.gif",S.getName());
		else if (S instanceof EntidadLibro) Act.setIcon("Book.gif",S.getName());
		else if (S instanceof TagEntity) Act.setIcon("Tag.gif",S.getName());
		else if (S instanceof EntidadUser) Act.setIcon("Users.gif",S.getName());
		else if (S instanceof EntidadAdmin) Act.setIcon("Admin.gif",S.getName());
		
		Act.setActual(ALL);
		Act.addClickHandler(BotonClick);
	}
	
	public void setBotonClick(ClickHandler botonClick) {
		BotonClick = botonClick;
	}
	
	public void setBotonTipo(BotonesStackPanelMio botonTipo) {
		BotonTipo = botonTipo;
	}
}
