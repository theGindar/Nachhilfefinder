/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author patrickguenther
 */
@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ExceptionResponse result = new ExceptionResponse();
        result.exception = ex.getClass().getName();
        result.message = ex.getMessage();

        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

}
