package lector.client.admin.tagstypes;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Grid;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.reader.IlegalFolderFusionException;
import lector.client.reader.LoadingPanel;


public class NewTypeRename extends PopupPanel {

    private GWTServiceAsync bookReaderServiceHolder = GWT.create(GWTService.class);
    private PopupPanel Yo;
    private  final TextBox textBox; 
    private final Entity Nombre;
    private Entity Father;

    public NewTypeRename(final Entity renombrar, Entity father) {
        super(false);
        setGlassEnabled(true);
        Father=father;
        String Tipo;
        if (renombrar instanceof File) Tipo="Type"; else Tipo="Type Category";
        String Titulo = "Write the new "+ Tipo +" for rename the " + Tipo + " " + '"' + renombrar.getName() + '"';
        Yo = this;
        Nombre =renombrar;
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        setWidget(verticalPanel);
        verticalPanel.setSize("100%", "100%");

        Label lblNewLabel = new Label(Titulo);
        lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.add(lblNewLabel);

        Grid grid = new Grid(1, 2);
        grid.setCellSpacing(2);
        grid.setCellPadding(2);
        verticalPanel.add(grid);
        grid.setSize("159px", "56px");

        Label lblType = new Label("Type");
        grid.setWidget(0, 0, lblType);
        
        VerticalPanel verticalPanel_1 = new VerticalPanel();
        verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        grid.setWidget(0, 1, verticalPanel_1);
        
        
        
        textBox = new TextBox();
        textBox.setVisibleLength(30);
        textBox.setMaxLength(100);
        verticalPanel_1.add(textBox);
        
        Grid grid_1 = new Grid(1, 2);
        verticalPanel.add(grid_1);
        
                Button btnNewButton = new Button("Save");
                grid_1.setWidget(0, 0, btnNewButton);
                btnNewButton.addClickHandler(new ClickHandler() {

                    public void onClick(ClickEvent event) {
                    	
                        	if (!textBox.getText().isEmpty()) {
                        		AsyncCallback<Long> callback = new AsyncCallback<Long>() {

                                    public void onFailure(Throwable caught) {
                                    	LoadingPanel.getInstance().hide();
                                    	Yo.hide();
                                        throw new UnsupportedOperationException("Not supported yet.");
                                        
                                    }

                                    public void onSuccess(Long result) {
                                       
                                    	LoadingPanel.getInstance().hide();
                                    	AsyncCallback<Void> callback = new AsyncCallback<Void>() {

                                            public void onFailure(Throwable caught) {
                                            	LoadingPanel.getInstance().hide();
                                            	if (caught instanceof IlegalFolderFusionException) {
                        							Window.alert(((IlegalFolderFusionException) caught)
                        									.getErrorMessage());
                        						} else {
                        							Window.alert("Error in Merge");
                        						}

                                                    Yo.hide();
                                              
                                              
                                            }

                                            public void onSuccess(Void result) {
                                            	LoadingPanel.getInstance().hide();
                                            	EditorTagsAndTypes.LoadBasicTypes();
                                            	 	Yo.hide();
                                               
                                            }
                                        };
                                        AsyncCallback<Integer> callback2=new AsyncCallback<Integer>() {
											
											public void onSuccess(Integer result) {
												LoadingPanel.getInstance().hide();
												EditorTagsAndTypes.LoadBasicTypes();
                                        	 	Yo.hide();
												
											}
											
											public void onFailure(Throwable caught) {
												Window.alert("Error in Merge");
												LoadingPanel.getInstance().hide();
                                                Yo.hide();
												
											}
										};
										LoadingPanel.getInstance().setLabelTexto("Saving...");
				    					LoadingPanel.getInstance().center();
										if (Nombre instanceof File)
                                        	bookReaderServiceHolder.fusionFiles( Nombre.getID(),result, callback2);
                                        else bookReaderServiceHolder.fusionFolder(Nombre.getID(),result,  callback);
                                    }
                                };
                                LoadingPanel.getInstance().setLabelTexto("Saving...");
		    					LoadingPanel.getInstance().center();
                                if (Nombre instanceof File){
                                	File file = new File(textBox.getText(),null,Nombre.getCatalogId());
                                	file.setFather(Nombre.getFather());
                                	bookReaderServiceHolder.saveFile(file, callback);
                                }
                                else {
                                	LoadingPanel.getInstance().setLabelTexto("Saving...");
			    					LoadingPanel.getInstance().center();
                                	Folder folder = new Folder(textBox.getText(),null,Nombre.getCatalogId());
                                	folder.setFather(Nombre.getFather());
                                	bookReaderServiceHolder.saveFolder(folder, callback); 
                                }
                        } else {
                            Window.alert("The new type is empty");
                            NewTypeRename NT = new NewTypeRename(renombrar,Father);
                            NT.isModal();
                            NT.center();
                            Yo.hide();
                        }
                        
                    }
                  
                });
                verticalPanel.setCellHorizontalAlignment(btnNewButton, HasHorizontalAlignment.ALIGN_CENTER);
                verticalPanel.setCellVerticalAlignment(btnNewButton, HasVerticalAlignment.ALIGN_MIDDLE);
                
                Button btnNewButton_1 = new Button("Cancel");
                btnNewButton_1.addClickHandler(new ClickHandler() {
                	public void onClick(ClickEvent event) {
                		Yo.hide();
                	}
                });
                grid_1.setWidget(0, 1, btnNewButton_1);
    }

   

}
