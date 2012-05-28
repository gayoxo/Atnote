package lector.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.LoggerService;
import lector.client.catalogo.server.FolderDB;
import lector.client.reader.BookBlob;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ExportServiceImpl extends RemoteServiceServlet implements
		ExportService {

	public void saveTemplate(Template template) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		if (template.getId() == null) {
			entityManager.persist(template);
		} else {

			entityManager.merge(template);
		}
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();
	}

	public void saveTemplateCategory(TemplateCategory templateCategory) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (templateCategory.getId() == null) {
			entityManager.persist(templateCategory);
		} else {

			entityManager.merge(templateCategory);
		}
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();
	}

	public void deleteTemplate(Long templateId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		List<Template> templates;
		entityTransaction.begin();
		String sql = "SELECT a FROM Template a WHERE a.id=" + templateId;
		templates = entityManager.createQuery(sql).getResultList();
		if (!templates.isEmpty()) {
			for (Long categoryId : templates.get(0).getCategories()) {
				deleteTemplateCategory(categoryId);
			}
			entityManager.remove(templates.get(0));
			entityTransaction.commit();
		}
	}

	public void deleteTemplateCategory(Long templateCategoryId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		List<TemplateCategory> templateCategorys;
		entityTransaction.begin();
		String sql = "SELECT a FROM TemplateCategory a WHERE a.id="
				+ templateCategoryId;
		templateCategorys = entityManager.createQuery(sql).getResultList();
		if (!templateCategorys.isEmpty()) {
			entityManager.remove(templateCategorys.get(0));
			entityTransaction.commit();
		}

	}

	public Template removeCategoriesFromTemplate(Long templateId,
			ArrayList<Long> categoriesIds) {
		Template template = loadTemplateById(templateId);
		for (Long categoryId : categoriesIds) {
			template.getCategories().remove(categoryId);
			deleteTemplateCategory(categoryId);
		}
		saveTemplate(template);
		return template;
	}

	public ArrayList<Template> getTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	public Template loadTemplateById(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoriesIds) {
		// TODO Auto-generated method stub
		return null;
	}

}
