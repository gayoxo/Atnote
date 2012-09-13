/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

import lector.share.model.Template;
import lector.share.model.TemplateCategory;

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

	public Template removeCategoriesFromTemplate(Long templateId,
			ArrayList<Long> categoriesIds);

	public ArrayList<Template> getTemplates();

	public Template loadTemplateById(Long Id);

	public ArrayList<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoriesIds);

	public ArrayList<Template> getTemplatesByIds(ArrayList<Long> ids);
	
	public void moveCategory(Long fromFatherId, Long toFatherId, Long categoryId, Long templateId);
	
	public void swapCategoryWeight(Long movingCategoryId,Long staticCategoryId);
	
	public ArrayList<Template> getTemplatesByProfessorId(Long professorId);

}
