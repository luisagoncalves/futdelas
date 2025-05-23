package org.ifb.tcc.futdelas.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ifb.tcc.futdelas.dtos.CampeonatoResponse;
import org.ifb.tcc.futdelas.dtos.ClassificacaoResponse;
import org.ifb.tcc.futdelas.dtos.PartidaResponse;
import java.util.List;

@RegisterRestClient(baseUri = "https://api.api-futebol.com.br/v1")
@Path("/campeonatos")
@Produces(MediaType.APPLICATION_JSON)
public interface ApiFutebolClient {
    @GET
    @Path("/30")
    CampeonatoResponse buscarCampeonatoPorId(@HeaderParam("Authorization") String token);

    @GET
    @Path("/30/tabela")
    List<ClassificacaoResponse> buscarClassificacaoPorCampeonatoId(@HeaderParam("Authorization") String token);

    @GET
    @Path("/30/partidas")
    PartidaResponse buscarPartidasPorCampeonatoId(@HeaderParam("Authorization") String token);
}
