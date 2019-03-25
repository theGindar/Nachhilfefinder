/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Offer;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.OfferStatus;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Subject;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Patrick Günther
 */
@Stateless
@RolesAllowed("app-user")
public class OfferBean extends EntityBean<Offer, Long> { 
   
    public OfferBean() {
        super(Offer.class);
    }
    
    public List<Offer> findByUsername(String username) {
        return em.createQuery("SELECT o FROM Offer o WHERE o.owner.username = :username ORDER BY o.startDate")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    public List<Offer> findByPrice(double min, double max) {
        return em.createQuery("SELECT t FROM Offer t WHERE t.price BETWEEN :min AND :max ORDER BY t.startDate")
                 .setParameter("min", min)
                 .setParameter("max", max)
                 .getResultList();
    }
    
    public Offer findById(long id) {
        List result = em.createQuery("SELECT t FROM Offer t WHERE t.id = :id")
                 .setParameter("id", id)
                 .getResultList();
        return (Offer)result.get(0);
    }
    
    // nach Titel suchen
    public List<Offer> search(String search, Subject subject, OfferStatus status) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Offer t
        CriteriaQuery<Offer> query = cb.createQuery(Offer.class);
        Root<Offer> from = query.from(Offer.class);
        query.select(from);

        // ORDER BY startDate
        query.orderBy(cb.asc(from.get("startDate")));
        
        // WHERE t.title LIKE :search
        Predicate p = cb.conjunction();
        
        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("title"), "%" + search + "%"));
            query.where(p);
        }
        
        // WHERE t.subject = :subject
        if (subject != null) {
            p = cb.and(p, cb.equal(from.get("subject"), subject));
            query.where(p);
        }
        
        // WHERE t.status = :status
        if (status != null) {
            p = cb.and(p, cb.equal(from.get("offerStatus"), status));
            query.where(p);
        }
        
        return em.createQuery(query).getResultList();
    }
}
