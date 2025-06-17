package org.ifb.tcc.futdelas.resources;

import java.util.List;

import org.ifb.tcc.futdelas.entities.Time;
import org.ifb.tcc.futdelas.services.TimeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeResource {

    @Inject
    TimeService service;

    @GET
    public List<Time> buscarTimesCampeonato() {
        return service.buscarTimesCampeonato();
    }
}

