package lector.client.controler;

import lector.client.admin.Administrador;
import lector.client.admin.activity.NewAdminActivities;
import lector.client.admin.admins.NewAdminAdministrator;
import lector.client.admin.book.BookAdministration;
import lector.client.admin.bookblob.BookLoader;
import lector.client.admin.catalog.NewAdminCatalogs;
import lector.client.admin.export.NewAdminTemplate;
import lector.client.admin.export.admin.EditTemplate;
import lector.client.admin.group.Groupadministration;
import lector.client.admin.langedit.EditordeLenguajes;
import lector.client.admin.langedit.NewAdminLangs;
import lector.client.admin.tagstypes.EditorTagsAndTypes;
import lector.client.admin.users.NewUserAdministrator;
import lector.client.browser.Browser;
import lector.client.login.ActualUser;
import lector.client.login.Login;
import lector.client.login.activitysel.MyActivities;
import lector.client.login.activitysel.VisorEntry;
import lector.client.login.bookselec.MyBooks;
import lector.client.reader.Book;
import lector.client.reader.MainEntryPoint;
import lector.client.reader.export.ExportResult;
import lector.client.reader.filter.advance.FilterAdvance;
import lector.client.search.Searcher;
import lector.client.welcome.Welcome;
import lector.client.login.UserEdition;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Controlador implements EntryPoint {


	
	private static Welcome WelcomePage = new Welcome();
    private static MainEntryPoint MEP = new MainEntryPoint();
    private static EntryPoint Actual = null;
    private static Searcher Search = new Searcher();
  //  private static ControladorEntrada controladorEntrada = new ControladorEntrada();
    private static Administrador Admin = new Administrador();
    private static EditorTagsAndTypes AdminTagsAndTypes = new EditorTagsAndTypes();
    private static Login LoginPage=new Login();
    private static BookAdministration bookAdministrador=new BookAdministration();
    private static MyBooks MyBooksUser=new MyBooks();
    private static Groupadministration GroupAdmin = new Groupadministration();
    private static NewUserAdministrator UserAdmin = new NewUserAdministrator();
    private static NewAdminAdministrator AdminAdmin = new NewAdminAdministrator();
    private static EditordeLenguajes EditorLenguaje = new EditordeLenguajes();
    private static NewAdminLangs AdminLenguaje = new NewAdminLangs();
    private static NewAdminCatalogs CatalogAdmin= new NewAdminCatalogs();
    private static NewAdminActivities Activitys= new NewAdminActivities();
    private static MyActivities MyActivities= new MyActivities();
    private static VisorEntry Visor=new VisorEntry();
    private static Browser Browser=new Browser();
    private static FilterAdvance FilterAdvance=new FilterAdvance();
    private static UserEdition UserEdition = new UserEdition();
	private static BookLoader BooKLoader=new BookLoader();
	private static NewAdminTemplate AdminTemplate=new NewAdminTemplate();
	private static ExportResult ExportResultado=new ExportResult();
	private static EditTemplate EditTemplateE=new EditTemplate();

    /**
     * @wbp.parser.entryPoint
     */
    public void onModuleLoad() {
        Actual = WelcomePage;
        Actual.onModuleLoad();
    }

    public static void clear() {
        RootPanel.get("Menu").clear();
        RootPanel.get("Original").clear();
        RootPanel.get("Etiquetas").clear();
        RootPanel.get("OriginalB").clear();
        RootPanel.get().clear();
    }

    public static void change2Welcome() {
        clear();
        Actual = WelcomePage;
        Actual.onModuleLoad();
    }
    
    public static void change2Reader() {
        
    	clear();
        Actual = MEP;
        Actual.onModuleLoad();
        Book book = ActualUser.getBook();
        
        MainEntryPoint.SetBook(book);
        MainEntryPoint.getTechnicalSpecs().setBook(book);
    }

    public static void change2Searcher() {
        clear();
        Actual = Search;
        Actual.onModuleLoad();
    }
    
   

//    public static void change2ControladorEntrada() {
//        clear();
//        Actual = controladorEntrada;
//        Actual.onModuleLoad();
//    }

    public static void change2Administrator() {
        clear();
        Actual = Admin;
        Actual.onModuleLoad();
    }
    
    public static void change2Login() {
        clear();
        Actual = LoginPage;
        Actual.onModuleLoad();
    }

	public static void change2EditorTagsAndTypes() {
		clear();
        Actual = AdminTagsAndTypes;
        Actual.onModuleLoad();
		
	}
	
	public static void change2BookAdminstrator() {
		clear();
        Actual = bookAdministrador;
        Actual.onModuleLoad();
		
	}
	
	public static void change2MyBooks(){
		clear();
		Actual = MyBooksUser;
		Actual.onModuleLoad();
	}
	
	public static void change2GroupAdministration() {
		clear();
		Actual = GroupAdmin;
		Actual.onModuleLoad();
		
	}
	
	public static void change2UserAdministration() {
		clear();
		Actual = UserAdmin;
		Actual.onModuleLoad();
		
	}

	public static void change2AdminAdministration() {
		clear();
		Actual = AdminAdmin;
		Actual.onModuleLoad();
		
	}

	public static void change2EditorLenguaje() {
		clear();
		Actual = EditorLenguaje;
		Actual.onModuleLoad();
		
	}
	
	public static void change2AdminLenguaje() {
		clear();
		Actual = AdminLenguaje;
		Actual.onModuleLoad();
		
	}
	
	public static void change2CatalogAdmin() {
		clear();
		Actual = CatalogAdmin;
		Actual.onModuleLoad();
		
	}
	
	public static void change2ActivityAdmin() {
		clear();
		Actual = Activitys;
		Actual.onModuleLoad();
		
	}

	public static void change2MyActivities() {
		clear();
		Actual = MyActivities;
		Actual.onModuleLoad();
		
	}

	public static void change2VisorEntry(String Path) {
		clear();
		Actual = Visor;
		Visor.setBook(Path);
		Actual.onModuleLoad();
		
	}
	
	public static void change2Browser() {
		clear();
		Actual = Browser;
		Actual.onModuleLoad();
		
	}
	
	public static void change2FilterAdvance() {
		clear();
		Actual = FilterAdvance;
		Actual.onModuleLoad();
		
	}

	public static void change2UserEdition() {
		clear();
		Actual = UserEdition;
		Actual.onModuleLoad();
	}

	public static void change2LoadABook() {
		clear();
		Actual = BooKLoader;
		Actual.onModuleLoad();
		
	}
	
	public static void change2AdminTemplate() {
		clear();
		Actual = AdminTemplate;
		Actual.onModuleLoad();
		
	}

	public static void change2ExportResult() {
		clear();
		Actual = ExportResultado;
		Actual.onModuleLoad();
		
	}

	public static void change2EditTemplate() {
		clear();
		Actual = EditTemplateE;
		Actual.onModuleLoad();
		
	}
}
