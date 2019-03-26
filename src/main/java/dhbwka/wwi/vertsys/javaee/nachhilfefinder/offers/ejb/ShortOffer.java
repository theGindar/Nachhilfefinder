/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.OfferStatus;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Subject;

/**
 *
 * @author patrickguenther
 */
public class ShortOffer {
    
    private long id;

    private String username;
    
    private Subject subject;

    private String title;

    private String description;
    
    private double price;
    
    private OfferStatus status = OfferStatus.OPEN;
    
    public ShortOffer(long id, String username, String title, String description, double price){
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.price = price;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    
    
}
