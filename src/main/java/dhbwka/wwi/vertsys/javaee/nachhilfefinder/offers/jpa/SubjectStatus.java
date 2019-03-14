/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa;

/**
 *
 * @author Jan Leyendecker
 */
public enum SubjectStatus {
    OPEN, IN_PROGRESS, FINISHED, CANCELED, POSTPONED;

    public String getLabel() {
        switch (this) {
            case OPEN:
                return "Offen";
            case IN_PROGRESS:
                return "In Bearbeitung";
            case FINISHED:
                return "Erledigt";
            case CANCELED:
                return "Abgebrochen";
            case POSTPONED:
                return "Zurückgestellt";
            default:
                return this.toString();
        }
    }

    
}
