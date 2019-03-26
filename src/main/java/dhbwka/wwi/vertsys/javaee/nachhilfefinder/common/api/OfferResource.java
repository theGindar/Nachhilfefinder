/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.api;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.OfferBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.ShortOffer;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Offer;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author patrickguenther
 */
//@Stateless
@Path("Offers")
@Produces(MediaType.APPLICATION_JSON)
public class OfferResource {
    @EJB
    OfferBean offerBean;
    
    @GET
    @Path("{id}")
    public ShortOffer getOfferById(@PathParam("id") long id){
        return convertOfferToShortOffer(this.offerBean.findById(id));
    }
    
    @GET
    @Path("query")
    public List<Offer> getOffersByPrice(@QueryParam("minprice") double min, @QueryParam("maxprice") double max){
        return this.offerBean.findByPrice(min, max);
    }
    
    @GET
    @Path("query")
    public List<Offer> getOffersByUsername(@QueryParam("username") String username){
        return this.offerBean.findByUsername(username);
    }
    
    public ShortOffer convertOfferToShortOffer(Offer offer){
        //long id, String username, String title, String description, double price, Date startDate
        ShortOffer sOffer = new ShortOffer(offer.getId(), offer.getOwner().getUsername(), offer.getTitle(), offer.getDescription(), offer.getPrice());
        return sOffer;
    }
    //@Path("query")
    //public List<Offer> getOffersByPrice()
    
}
