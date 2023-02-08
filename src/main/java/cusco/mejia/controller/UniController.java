package cusco.mejia.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import cusco.mejia.service.ReactiveService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import lombok.extern.slf4j.Slf4j;

@Path("/uni")
@Produces("application/json")
@Slf4j
public class UniController {

    @Inject
    ReactiveService reactiveService;

    @GET
    public Uni<Response> get() {
        long startTime = System.currentTimeMillis();
        return reactiveService.procesar()
                .onItem().transform(dto -> Response.ok(Json.encode(dto)).build())
                .onItem().invoke(dto -> {
                    long endTime = System.currentTimeMillis();
                    log.info("Time: {}ms", endTime - startTime);
                });
    }

}
