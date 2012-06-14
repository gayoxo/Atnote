package lector.server;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.controler.Constants;

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

		if (templateCategory.getFatherId().equals(Constants.TEMPLATEID)) {
			Template template = loadTemplateById(templateCategory
					.getTemplateId());
			//Template templateToSave = swapTemplate(template);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			template.getCategories().add(templateCategory.getId());
			saveTemplate(template);
		} else {
			TemplateCategory templateCategoryFather = loadTemplateCategoryById(templateCategory
					.getFatherId());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			templateCategoryFather.getSubCategories().add(
					templateCategory.getId());
			savePlainCategory(templateCategoryFather);
		}

	}

	public void savePlainCategory(TemplateCategory templateCategory) {
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

	public ArrayList<Template> getTemplatesByIds(ArrayList<Long> ids) {
		ArrayList<Template> templates = new ArrayList<Template>();
		for (int i = 0; i < ids.size(); i++) {
			Template tmpl = loadTemplateById(ids.get(i));
			templates.add(tmpl);
		}

		return templates;
	}

	public Template loadTemplateById(Long templateId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Template> list;
		ArrayList<Template> templates;
		String sql = "SELECT r FROM Template r WHERE r.id=" + templateId;
		list = entityManager.createQuery(sql).getResultList();
		templates = new ArrayList<Template>(list);

		Template template = null;
		if (!list.isEmpty()) {
			template = templates.get(0);
			java.util.ArrayList<Long> categories = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) template.getCategories());
			template.getCategories().clear();
			template.setCategories(categories);

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return template;
	}

	public ArrayList<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoryIds) {
		ArrayList<TemplateCategory> categories = new ArrayList<TemplateCategory>();
		for (int i = 0; i < categoryIds.size(); i++) {
			TemplateCategory tmpl = loadTemplateCategoryById(categoryIds.get(i));
			categories.add(tmpl);
		}

		return categories;
	}

	private TemplateCategory loadTemplateCategoryById(Long templateCategoryId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<TemplateCategory> list;
		ArrayList<TemplateCategory> templateCategorys;
		String sql = "SELECT r FROM TemplateCategory r WHERE r.id="
				+ templateCategoryId;
		list = entityManager.createQuery(sql).getResultList();
		templateCategorys = new ArrayList<TemplateCategory>(list);

		TemplateCategory templateCategory = null;
		if (!list.isEmpty()) {
			templateCategory = templateCategorys.get(0);
			java.util.ArrayList<Long> subCategories = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) templateCategory
							.getSubCategories());
			java.util.ArrayList<Long> annotationIds = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) templateCategory
							.getAnnotationsIds());
			templateCategory.getSubCategories().clear();
			templateCategory.getAnnotationsIds().clear();
			templateCategory.setSubCategories(subCategories);
			templateCategory.setAnnotationsIds(annotationIds);

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return templateCategory;
	}

	public ArrayList<Template> getTemplates() {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<Template> list;
		ArrayList<Template> listTemplates;
		String sql = "SELECT a FROM Template a";
		list = entityManager.createQuery(sql).getResultList();
		listTemplates = new ArrayList<Template>(list);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		for (int i = 0; i < listTemplates.size(); i++) {
			listTemplates.get(i).setCategories(
					new ArrayList<Long>(listTemplates.get(i).getCategories()));
		}

		return listTemplates;
	}

	public void moveCategory(Long fromFatherId, Long toFatherId, Long categoryId) {
		removeCategoryFromParent(fromFatherId, categoryId);
		addNewFatherToCategory(toFatherId, categoryId);
		if (toFatherId.equals(Constants.TEMPLATEID)) {
			addChildToTemplate(categoryId, toFatherId);
		} else {
			addChildToCategory(categoryId, toFatherId);
		}

	}

	private void addChildToCategory(Long categoryId, Long toFatherId) {
		TemplateCategory templateCategory = loadTemplateCategoryById(toFatherId);
		templateCategory.getSubCategories().add(categoryId);
		saveTemplateCategory(templateCategory);

	}

	private void addChildToTemplate(Long categoryId, Long toFatherId) {
		Template template = loadTemplateById(toFatherId);
		template.getCategories().add(categoryId);
		saveTemplate(template);
	}

	private void addNewFatherToCategory(Long toFatherId, Long categoryId) {
		TemplateCategory templateCategory = loadTemplateCategoryById(categoryId);
		templateCategory.setFatherId(toFatherId);
		saveTemplateCategory(templateCategory);

	}

	private void removeCategoryFromParent(Long fromFatherId, Long categoryId) {
		TemplateCategory templateCategoryFather = loadTemplateCategoryById(fromFatherId);
		if (templateCategoryFather.getSubCategories().contains(categoryId)) {
			templateCategoryFather.getSubCategories().remove(categoryId);
			saveTemplateCategory(templateCategoryFather);
		}

	}

}
