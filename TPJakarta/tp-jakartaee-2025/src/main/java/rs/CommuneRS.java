package rs;

import ejb.CommuneEJB;
import entity.Commune;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/commune")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CommuneRS {

    @EJB
    private CommuneEJB communeEJB;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("nom") String nom,
                           @FormParam("region") String region) {

        Commune c = new Commune();
        c.setNom(nom);
        c.setRegion(region);

        communeEJB.createCommune(c);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Commune c = communeEJB.findCommuneById(id);
        if (c == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(c).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response update(@PathParam("id") Long id,
                           @FormParam("nom") String nom,
                           @FormParam("region") String region) {

        Commune c = communeEJB.findCommuneById(id);
        if (c == null) {
            c = new Commune();
            c.setNom(nom);
            c.setRegion(region);
            communeEJB.createCommune(c);
            return Response.status(Response.Status.CREATED).build();
        }

        c.setNom(nom);
        c.setRegion(region);
        communeEJB.updateCommune(c);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = communeEJB.deleteCommuneById(id);
        if (!deleted)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    public Response findAll() {
        List<Commune> list = communeEJB.findAllCommune();
        if (list.isEmpty())
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("La base de donnée des communes est vide")
                    .build();
        return Response.ok(list).build();
    }
}
