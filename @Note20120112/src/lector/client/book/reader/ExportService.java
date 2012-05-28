/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.login.GroupApp;
import lector.client.reader.Book;
import lector.client.reader.BookNotFoundException;
import lector.client.reader.GeneralException;
import lector.client.reader.NullParameterException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/exportservice")
public interface ExportService extends RemoteService {

	public void saveTemplate(Template template);

	public void saveTemplateCategory(TemplateCategory templateCategory);

	public void deleteTemplate(Long templateId);

	public void deleteTemplateCategory(Long templateCategoryId);

	public Template removeCategoriesFromTemplate(Long templateId, ArrayList<Long> categoriesIds);

	public ArrayList<Template> getTemplates();

	public Template loadTemplateById(Long Id);

	public ArrayList<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoriesIds);

}
