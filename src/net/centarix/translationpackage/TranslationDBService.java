/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.centarix.translationpackage;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
/**
 *
 * @author klove
 */
public class TranslationDBService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
    
    public TranslationItem createTranslationItem(String englishText, String chineseText) {

        TranslationItem translation = new TranslationItem(englishText, chineseText, "");
        
        if(findTranslationByEnglishText(englishText) == null)
        {
            tx.begin();
            em.persist(translation);
            tx.commit();
            return translation;
        }
        return translation;
    }
    
    public TranslationItem findTranslationByEnglishText(String englishText) {
        TranslationItem translation = new TranslationItem();
        
        tx.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TranslationItem> q = cb.createQuery(TranslationItem.class);
        
        //build the criteriaquery
        Root<TranslationItem> t = q.from(TranslationItem.class);
        ParameterExpression<String> p = cb.parameter(String.class);
        q.select(t).where(cb.equal(t.get("EnglishText"), p));
        
        //populate the parameter
        TypedQuery<TranslationItem> query = em.createQuery(q);
        query.setParameter(p, englishText);
        List<TranslationItem> translationList = query.getResultList();
        tx.commit();
        if(translationList.size() > 0)
            return translationList.get(0);
        return null;
    }
    
    public void removeTranslationItem(long Id)
    {
        TranslationItem itemToBeDeleted = em.find(TranslationItem.class, Id);
        if(itemToBeDeleted != null)
        {
            tx.begin();
            em.remove(itemToBeDeleted);
            tx.commit();            
        }
    }
}
