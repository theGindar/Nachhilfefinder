/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Subject;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author patrickguenther
 */
@Stateless
@RolesAllowed("app-user")
public class SubjectBean extends EntityBean<Subject, Long> {

    public SubjectBean() {
        super(Subject.class);
    }

    /**
     * Auslesen aller Fächer, alphabetisch sortiert.
     *
     * @return Liste mit allen Fächern
     */
    public List<Subject> findAllSorted() {
        return this.em.createQuery("SELECT s FROM Subject s ORDER BY s.name").getResultList();
    }
}
