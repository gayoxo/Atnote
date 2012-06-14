package lector.client.book.reader;

import java.util.ArrayList;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExportServiceAsync {

	void saveTemplate(Template template, AsyncCallback<Void> callback);

	void saveTemplateCategory(TemplateCategory templateCategory,
			AsyncCallback<Void> callback);

	void deleteTemplate(Long templateId, AsyncCallback<Void> callback);

	void deleteTemplateCategory(Long templateCategoryId,
			AsyncCallback<Void> callback);

	void getTemplates(AsyncCallback<ArrayList<Template>> callback);

	void loadTemplateById(Long Id, AsyncCallback<Template> callback);

	void removeCategoriesFromTemplate(Long templateId, ArrayList<Long> categoriesIds,
			AsyncCallback<Template> callback);

	void getTemplateCategoriesByIds(ArrayList<Long> categoriesIds,
			AsyncCallback<ArrayList<TemplateCategory>> callback);

	void getTemplatesByIds(ArrayList<Long> ids,
			AsyncCallback<ArrayList<Template>> callback);

	void moveCategory(Long fromFatherId, Long toFatherId, Long categoryId,
			Long templateId, AsyncCallback<Void> callback);



}
