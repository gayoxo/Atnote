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

		 try {
		 Thread.sleep(1000l);
		 } catch (InterruptedException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }

		if (templateCategory.getFatherId().equals(Constants.TEMPLATEID)) {
			Template template = loadTemplateById(templateCategory
					.getTemplateId());
			template.getCategories().add(templateCategory.getId());
			Template templateToSave = swapTemplate(template);
			saveTemplate(templateToSave);
		} else {
			TemplateCategory templateCategoryFather = loadTemplateCategoryById(templateCategory
					.getFatherId());
			templateCategoryFather.getSubCategories().add(
					templateCategory.getId());
			TemplateCategory categoryToSave = swapCategory(templateCategoryFather);
			savePlainCategory(categoryToSave);
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
			for (Long categoryId : templateCategorys.get(0).getSubCategories()) {
				deleteTemplateCategory(categoryId);
			}
			entityManager.remove(templateCategorys.get(0));
			entityTransaction.commit();
			entityManager.close();

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

	public void moveCategory(Long fromFatherId, Long toFatherId,
			Long categoryId, Long templateId) {
		removeCategoryFromParent(fromFatherId, categoryId, templateId);
		addNewFatherToCategory(toFatherId, categoryId);
		if (toFatherId.equals(Constants.TEMPLATEID)) {
			addChildToTemplate(categoryId, templateId);
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
		savePlainCategory(templateCategory);

	}

	private void removeCategoryFromParent(Long fromFatherId, Long categoryId,
			Long templateId) {
		if (fromFatherId.equals(Constants.TEMPLATEID)) {
			Template template = loadTemplateById(templateId);
			if (template.getCategories().contains(categoryId)) {
				template.getCategories().remove(categoryId);
				saveTemplate(template);
			}
		} else {
			TemplateCategory templateCategoryFather = loadTemplateCategoryById(fromFatherId);
			if (templateCategoryFather.getSubCategories().contains(categoryId)) {
				templateCategoryFather.getSubCategories().remove(categoryId);
				TemplateCategory categoryToSave = swapCategory(templateCategoryFather);
				savePlainCategory(categoryToSave);
			}

		}

	}

	private TemplateCategory swapCategory(
			TemplateCategory templateCategoryFather) {
		TemplateCategory templateCategory = new TemplateCategory();
		templateCategory.setId(templateCategoryFather.getId());
		templateCategory.setFatherId(templateCategoryFather.getFatherId());
		templateCategory.setName(templateCategoryFather.getName());
		templateCategory.setOrder(templateCategoryFather.getOrder());
		templateCategory.setSubCategories(templateCategoryFather
				.getSubCategories());
		templateCategory.setTemplateId(templateCategoryFather.getTemplateId());
		return templateCategory;
	}

	private Template swapTemplate(Template templateToSwap) {
		Template template = new Template();
		template.setId(templateToSwap.getId());
		template.setName(templateToSwap.getName());

		template.setCategories(templateToSwap.getCategories());
		template.setUserApp(templateToSwap.getUserApp());
		return template;
	}

}
