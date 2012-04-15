package lector.client.language;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;

	//Generales
    @Id
    private String nameId;
    //MainWindow
    private String NamePage = "@Note";
    private String Specifications = "Book Card";
    private String Annotation = "Annotation";
    private String No_Annotation = "No Annotations";
    private String All_Annotation = "All Annotations";
    private String Only_Selected = "On mouse over";
    private String BackAdministrationButton = "Administration";
    private String BackUserButton = "Back";
    private String FilterMainButton = "Filter";
    private String BrowserMainButton = "Browser";
    private String AnnotationsFiltering="Active Filter";
    private String DOYOUFilterOUT="Do you want to desactivate the filter?";
    private String AcceptFilter="Accept";
    private String CancelFilter="Cancel";
    private String ShowDensity = "Show Density";
    
    //Specifications
    private String ID = "ID";
    private String Authors = "Authors";
    private String Pages = "Pages";
    private String Publication_Year = "Publication Year";
    private String Title = "Title";
    private String Front_Cover = "Front_Cover";
    //Filter
    private String Advance = "Advance";
    private String FilterButton = "Filter";
    private String Select_All = "Select All";
    private String AllSelected = "All Selected";
    //Advance
    private String CloseFA="Close";
    private String FilterButtonFA="Filter";
    private String New_Rule="New Rule";
    private String Remove="Remove";
    private String GO_To_PageFA="Go to Page";
    private String Comment_AreaFA="Comment Area";
    private String PageFA="Page";
    private String Rule="Rule";
    //Annotation
    private String Save = "Save";
    private String ClearAnot = "Clear";
    private String Cancel = "Cancel";
    private String DeleteAnnotation = "Delete Annotation";
    private String SetTypes = "Teacher Catalog";
	private String SetTypesPublic = "Student Catalog";
    private String Visibility = "Visibility";
    private String Upgradeable = "Public Editable?";
    private String New="New";
    private String FromExist="Reuse";
	private String NewAdmin = "Admin";
	private String Done="Done";
    //Varios
    private String E_Page_Dont_Exist="Error: Page does not exist";
    private String E_Not_a_number="Error: Page number could not be a letter";
    private String Loading="Loading...";
    private String Saving="Saving...";
    private String Deleting="Deleting...";
    private String Filtering="Filtering...";
    private String E_Coments_dont_be_refresh="Error: Comments could not be refreshed";
    private String E_Need_to_select_a_type="Error: Select a Type from ";
    private String W_Newer_version_of_anotation="Warning : There's a newer version for this Annotacion, Are you sure you want to save it anyway?";
    private String E_Tags_Refresh="Error : Tags cannot be refreshed";
    private String E_Types_refresh="Error : Types cannot be refreshed";
    private String E_Empty_Or_2Less_Tag="Error : Tags must be more than 2 letters long";
    private String E_Tag_Dont_Exist="Error : Tag does not exist";
    private String E_loading="Error Loading";
    private String E_saving="Error Saving";
    private String E_deleting="Error Deleting";	
    private String E_ExistBefore = "Error: This type was added Before";
    private String E_typeFilter= "Error: Filter found problems when retrieving types";
    private String E_UserLoad="Error: Users can't be load";
    private String E_filteringmesagetypes="Error filtering Message: Load Types";
    private String E_filteringmesageAnnotations="Error filtering Message: Load Annotations";
    private String E_Saving="Error: Error Saving";
	private String E_DeleteReply = "Error: Error Deleting Reply, Please Try Again";
   //Browser
    private String Close="Close";
    private String FilterButtonBrowser="Filter";
    private String GO_To_Page="Go to Page";
    private String Comment_Area="Comment Area";
    private String Page="Page";
    private String TeacherTypes = "Teacher Types";
	private String OpenTypes = "Open Types";

    
    
    public Language() {
    }

    //TODO Generales
    public Language(String name) {
        nameId = name;
    }

    public String getName() {
        	return nameId;
    }

    public void setName(String name) {
        this.nameId = name;
    }

    //TODO MainWindow
    public String getNamePage() {
    	return NamePage;
    }

    public void setNamePage(String namePage) {
        NamePage = namePage;
    }

    public String getSpecifications() {
        return Specifications;
    }

    public void setSpecifications(String specifications) {
        Specifications = specifications;
    }

    public String getAnnotation() {
        return Annotation;
    }

    public void setAnnotation(String annotation) {
        Annotation = annotation;
    }

    public String getNo_Annotation() {
        return No_Annotation;
    }

    public void setNo_Annotation(String no_Annotation) {
        No_Annotation = no_Annotation;
    }

    public String getAll_Annotation() {
        return All_Annotation;
    }

    public void setAll_Annotation(String all_Annotation) {
        All_Annotation = all_Annotation;
    }

    public String getOnly_Selected() {
        return Only_Selected;
    }

    public void setOnly_Selected(String only_Selected) {
        Only_Selected = only_Selected;
    }

    public String getBackAdministrationButton() {
        return BackAdministrationButton;
    }

    public void setBackAdministrationButton(String backAdministrationButton) {
        BackAdministrationButton = backAdministrationButton;
    }

    public String getBackUserButton() {
        return BackUserButton;
    }

    public void setBackUserButton(String backUserButton) {
        BackUserButton = backUserButton;
    }
    
    public void setBrowserMainButton(String browserMainButton) {
		BrowserMainButton = browserMainButton;
	}
    
    public void setFilterMainButton(String filterMainButton) {
		FilterMainButton = filterMainButton;
	}
    
    public String getBrowserMainButton() {
		return BrowserMainButton;
	}
    
    public String getFilterMainButton() {
		return FilterMainButton;
	}
    
    public String getAnnotationsFiltering() {
		return AnnotationsFiltering;
	}
    
    public void setAnnotationsFiltering(String annotationsFiltering) {
		AnnotationsFiltering = annotationsFiltering;
	}
    
    public String getDOYOUFilterOUT() {
		return DOYOUFilterOUT;
	}
    
    public void setDOYOUFilterOUT(String dOYOUFilterOUT) {
		DOYOUFilterOUT = dOYOUFilterOUT;
	}
    
    public String getAcceptFilter() {
		return AcceptFilter;
	}
    
    public void setAcceptFilter(String acceptFilter) {
		AcceptFilter = acceptFilter;
	}
    
    public void setCancelFilter(String cancelFilter) {
		CancelFilter = cancelFilter;
	}
    
    public String getCancelFilter() {
		return CancelFilter;
	}
    
    public String getShowDensity() {
    	return ShowDensity;
    }

   public void setShowDensity(String showDensity) {
	ShowDensity = showDensity;
}
    
    //TODO Specifications
    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getPages() {
        return Pages;
    }

    public void setPages(String pages) {
        Pages = pages;
    }

    public String getPublication_Year() {
        return Publication_Year;
    }

    public void setPublication_Year(String publication_Year) {
        Publication_Year = publication_Year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFront_Cover() {
        return Front_Cover;
    }

    public void setFront_Cover(String front_Cover) {
        Front_Cover = front_Cover;
    }

    //TODO Filter
    public String getAdvance() {
        return Advance;
    }

    public void setAdvance(String advance) {
    	Advance = advance;
    }

    public String getFilterButton() {
        return FilterButton;
    }

    public void setFilterButton(String filterButton) {
        FilterButton = filterButton;
    }

    public String getSelect_All() {
        return Select_All;
    }

    public void setSelect_All(String select_All) {
        Select_All = select_All;
    }
  //Advance

    public String getCloseFA() {
    	return CloseFA;
    }

    public String getComment_AreaFA() {
    	return Comment_AreaFA;
    }

    public String getFilterButtonFA() {
    	return FilterButtonFA;
    }

    public String getGO_To_PageFA() {
    	return GO_To_PageFA;
    }

    public String getNew_Rule() {
    	return New_Rule;
    }

    public void setCloseFA(String closeFA) {
    	CloseFA = closeFA;
    }

    public void setComment_AreaFA(String comment_AreaFA) {
    	Comment_AreaFA = comment_AreaFA;
    }

    public void setFilterButtonFA(String filterButtonFA) {
    	FilterButtonFA = filterButtonFA;
    }

    public void setGO_To_PageFA(String gO_To_PageFA) {
    	GO_To_PageFA = gO_To_PageFA;
    }

    public void setNew_Rule(String new_Rule) {
    	New_Rule = new_Rule;
    }

    public String getRemove() {
    	return Remove;
    }

    public void setRemove(String remove) {
    	Remove = remove;
    }

    public String getPageFA() {
    	return PageFA;
    }

    public void setPageFA(String pageFA) {
    	PageFA = pageFA;
    }

    public String getRule() {
    	return Rule;
    }

    public void setRule(String rule) {
    	Rule = rule;
    }


    //TODO Annotation
    public String getSave() {
        return Save;
    }

    public void setSave(String save) {
        Save = save;
    }

    public String getClearAnot() {
        return ClearAnot;
    }

    public void setClearAnot(String clearAnot) {
        ClearAnot = clearAnot;
    }

    public String getCancel() {
        return Cancel;
    }

    public void setCancel(String cancel) {
        Cancel = cancel;
    }

    public String getDeleteAnnotation() {
        return DeleteAnnotation;
    }

    public void setDeleteAnnotation(String DeleteAnnotation) {
        this.DeleteAnnotation = DeleteAnnotation;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }


    public String getSetTypes() {
        return SetTypes;
    }
    
    public String getSetTypesPublic() {
    	return SetTypesPublic;
    }
    
	public void setSetTypesPublic(String setTypesPublic) {
		SetTypesPublic = setTypesPublic;
	}
    public void setSetTypes(String setTypes) {
        SetTypes = setTypes;
    }

    public String getVisibility() {
        return Visibility;
    }

    public void setVisibility(String visibility) {
        Visibility = visibility;
    }

    public String getUpgradeable() {
        return Upgradeable;
    }

    public void setUpgradeable(String upgradeable) {
        Upgradeable = upgradeable;
    }
    
    public String getNew() {
		return New;
	}
    
    public void setNew(String new1) {
		New = new1;
	}

    public String getFromExist() {
		return FromExist;
	}

	public void setFromExist(String fromExist) {
		FromExist = fromExist;
	}

	public String getNewAdmin() {
		return NewAdmin;
	}
	
	public void setNewAdmint(String newAdmin) {
		NewAdmin=newAdmin;
		
	}
	
	public void setDone(String done) {
		Done = done;
	}
	
	public String getDone() {
		return Done;
	}
	
	//TODO Varios
	public String getE_Page_Dont_Exist() {
		return E_Page_Dont_Exist;
	}

	public void setE_Page_Dont_Exist(String e_Page_Dont_Exist) {
		E_Page_Dont_Exist = e_Page_Dont_Exist;
	}

	public String getE_Not_a_number() {
		return E_Not_a_number;
	}

	public void setE_Not_a_number(String e_Not_a_number) {
		E_Not_a_number = e_Not_a_number;
	}

	public String getLoading() {
		return Loading;
	}

	public void setLoading(String loading) {
		Loading = loading;
	}

	public String getSaving() {
		return Saving;
	}

	public void setSaving(String saving) {
		Saving = saving;
	}

	public String getDeleting() {
		return Deleting;
	}

	public void setDeleting(String deleting) {
		Deleting = deleting;
	}

	public String getFiltering() {
		return Filtering;
	}
	
	public void setFiltering(String filtering) {
		Filtering = filtering;
	}
	
	public String getE_Coments_dont_be_refresh() {
		return E_Coments_dont_be_refresh;
	}

	public void setE_Coments_dont_be_refresh(String e_Coments_dont_be_refresh) {
		E_Coments_dont_be_refresh = e_Coments_dont_be_refresh;
	}

	public String getE_Need_to_select_a_type() {
		return E_Need_to_select_a_type;
	}

	public void setE_Need_to_select_a_type(String e_Need_to_select_a_type) {
		E_Need_to_select_a_type = e_Need_to_select_a_type;
	}

	public String getW_Newer_version_of_anotation() {
		return W_Newer_version_of_anotation;
	}

	public void setW_Newer_version_of_anotation(String w_Newer_version_of_anotation) {
		W_Newer_version_of_anotation = w_Newer_version_of_anotation;
	}

	public String getE_Tags_Refresh() {
		return E_Tags_Refresh;
	}

	public void setE_Tags_Refresh(String e_Tags_Refresh) {
		E_Tags_Refresh = e_Tags_Refresh;
	}

	public String getE_Types_refresh() {
		return E_Types_refresh;
	}

	public void setE_Types_refresh(String e_Types_refresh) {
		E_Types_refresh = e_Types_refresh;
	}

	public String getE_Empty_Or_2Less_Tag() {
		return E_Empty_Or_2Less_Tag;
	}

	public void setE_Empty_Or_2Less_Tag(String e_Empty_Or_2Less_Tag) {
		E_Empty_Or_2Less_Tag = e_Empty_Or_2Less_Tag;
	}

	public String getE_Tag_Dont_Exist() {
		return E_Tag_Dont_Exist;
	}

	public void setE_Tag_Dont_Exist(String e_Tag_Dont_Exist) {
		E_Tag_Dont_Exist = e_Tag_Dont_Exist;
	}

	public String getE_loading() {
		return E_loading;
	}

	public void setE_loading(String e_loading) {
		E_loading = e_loading;
	}

	public String getE_saving() {
		return E_saving;
	}

	public void setE_saving(String e_saving) {
		E_saving = e_saving;
	}
    
	public String getE_deleting() {
		return E_deleting;
	}
	
	public void setE_deleting(String e_deleting) {
		E_deleting = e_deleting;
	}

	
public String getAllSelected() {
	return AllSelected;
}

public void setAllSelected(String allSelected) {
	AllSelected = allSelected;
}

public String getE_ExistBefore() {
	return E_ExistBefore;
}

public void setE_ExistBefore(String e_ExistBefore) {
	E_ExistBefore = e_ExistBefore;
}

public String getE_typeFilter() {
	return E_typeFilter;
}

public void setE_typeFilter(String e_typeFilter) {
	E_typeFilter = e_typeFilter;
}
 

public String getE_UserLoad() {
	return E_UserLoad;
}

public void setE_UserLoad(String e_UserLoad) {
	E_UserLoad = e_UserLoad;
}


public String getE_filteringmesageAnnotations() {
	return E_filteringmesageAnnotations;
}

public String getE_filteringmesagetypes() {
	return E_filteringmesagetypes;
}

public void setE_filteringmesageAnnotations(String e_filteringmesageAnnotations) {
	E_filteringmesageAnnotations = e_filteringmesageAnnotations;
}


public void setE_filteringmesagetypes(String e_filteringmesagetypes) {
	E_filteringmesagetypes = e_filteringmesagetypes;
}

public String getE_DeleteReply() {
	return E_DeleteReply;
}

public void setE_DeleteReply(String e_DeleteReply) {
	E_DeleteReply = e_DeleteReply;
}

//TODO Browser

public String getE_Saving() {
	return E_Saving;
}

public void setE_Saving(String e_Saving) {
	E_Saving = e_Saving;
}

public String getClose() {
	return Close;
}

public void setClose(String close) {
	Close = close;
}

public String getFilterButtonBrowser() {
	return FilterButtonBrowser;
}

public void setFilterButtonBrowser(String filterButtonBrowser) {
	FilterButtonBrowser = filterButtonBrowser;
}

public String getGO_To_Page() {
	return GO_To_Page;
}

public void setGO_To_Page(String gO_To_Page) {
	GO_To_Page = gO_To_Page;
}

public String getComment_Area() {
	return Comment_Area;
}

public void setComment_Area(String comment_Area) {
	Comment_Area = comment_Area;
}

public String getPage() {
	return Page;
}

public void setPage(String page) {
	Page = page;
}


public String getTeacherTypes() {
	return TeacherTypes;
}

public String getOpenTypes() {
	return OpenTypes;
}

 public void setTeacherTypes(String teacherTypes) {
	TeacherTypes = teacherTypes;
}

public void setOpenTypes(String openTypes) {
	OpenTypes = openTypes;
}





}

