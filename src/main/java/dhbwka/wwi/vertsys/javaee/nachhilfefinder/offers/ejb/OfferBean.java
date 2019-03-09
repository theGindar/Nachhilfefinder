/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Offer;
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
        return em.createQuery("SELECT t FROM Task t WHERE t.owner.username = :username ORDER BY t.startDate")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    // nach Titel suchen
    public List<Offer> search(String search) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Task t
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
        
        return em.createQuery(query).getResultList();
    }
}
