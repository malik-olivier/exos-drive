package fr.eservices.drive.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.model.Perishable;

public class CatalogDaoJPAImpl implements CatalogDao {

    @PersistenceContext(name = "myApp")
    private EntityManager em;

    public CatalogDaoJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Category> getCategories() {
        Query query = em.createQuery("SELECT c from Category c order by c.orderIdx", Category.class);
        return query.getResultList();
    }

    @Override
    public List<Category> getArticleCategories(int id) throws DataException {
        Article article = em.find(Article.class, id);
        if (article == null) {
            throw new DataException("Article doesn't exists");
        }
        return article.getCategories();
    }

    @Override
    public List<Article> getCategoryContent(int categoryId) throws DataException {
        if(em.find(Category.class,categoryId) == null){
            throw new DataException("category doesn't exits");
        }
        Query query = em.createQuery(
                "SELECT a2 from Article a2" +
                        " join a2.categories c " +
                        " where c.id=:id ", Article.class);
        query.setParameter("id", categoryId);
        List<Article> articles = (List<Article>) query.getResultList();
        return (articles != null ? articles : new ArrayList<>());
    }

    @Override
    public List<Perishable> getPerished(Date day) throws DataException {
        Query query = em.createQuery("SELECT p from Perishable p where :day >= p.bestBefore", Perishable.class);
        query.setParameter("day",day);
        List<Perishable> perishables = (List<Perishable>) query.getResultList();
        if (perishables !=null && perishables.size() >= 200) {
            throw new DataException("Many product perished");
        }
        return (perishables != null ? perishables : new ArrayList<>());
    }


}
