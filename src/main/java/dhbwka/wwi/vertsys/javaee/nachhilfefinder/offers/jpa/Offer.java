/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.tasks.jpa.TaskStatus;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    
    @ManyToOne
    private Subject subject;

    @Column(length = 50)
    @NotNull(message = "Der Titel darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String title;

    @Lob
    @NotNull (message = "Die Beschreibung darf nicht leer sein.")
    private String description;
    
    @Column(precision = 5, scale = 2)
    @Min(value = (long) 0.00, message = "Der Preis darf nicht kleiner als 0.00€ sein.")
    private double price;

    @NotNull(message = "Das voraussichtliche Startdatum darf nicht leer sein.")
    private Date startDate;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private OfferStatus status = OfferStatus.OPEN;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Offer() {
    }

    public Offer(User owner, String title, String description, Date startDate, double price) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.price = price;
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
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    //</editor-fold>

}
