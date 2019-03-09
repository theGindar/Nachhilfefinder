/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Patrick Günther
 */
@Entity
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "offer_ids")
    @TableGenerator(name = "offer_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Das Angebot muss einem Benutzer zugeordnet werden.")
    private User owner;

    @Column(length = 50)
    @NotNull(message = "Der Titel darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String title;

    @Lob
    @NotNull (message = "Die Beschreibung darf nicht leer sein.")
    private String description;

    @NotNull(message = "Das voraussichtliche Startdatum darf nicht leer sein.")
    private Date startDate;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Offer() {
    }

    public Offer(User owner, String title, String description, Date startDate) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    //</editor-fold>

}
