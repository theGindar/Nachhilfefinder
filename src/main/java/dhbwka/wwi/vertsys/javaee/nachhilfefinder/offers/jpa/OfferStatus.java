/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa;

import javax.persistence.Entity;

/**
 *
 * @author Jan Leyendecker
 */
public enum OfferStatus {
    OPEN, INTERESTED, CLOSED;

    public String getLabel() {
        switch (this) {
            case OPEN:
                return "Offen";
            case INTERESTED:
                return "Es gibt Interessenten";
            case CLOSED:
                return "Nicht mehr verfügbar";
            default:
                return this.toString();
        }
    }

    
}
