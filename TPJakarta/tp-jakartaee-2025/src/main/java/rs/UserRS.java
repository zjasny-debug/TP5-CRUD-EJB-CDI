package rs;

import entity.User;
import service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/user")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserRS {

    @Inject
    private UserService userService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("nom") String nom,
                           @FormParam("prenom") String prenom) {

        User u = new User();
        u.setNom(nom);
        u.setPrenom(prenom);
        userService.createUser(u);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        User u = userService.findUserById(id);
        if (u == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(u).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response update(@PathParam("id") Long id,
                           @FormParam("nom") String nom,
                           @FormParam("prenom") String prenom) {

        User u = userService.findUserById(id);
        if (u == null) {
            u = new User();
            u.setNom(nom);
            u.setPrenom(prenom);
            userService.createUser(u);
            return Response.status(Response.Status.CREATED).build();
        }

        u.setNom(nom);
        u.setPrenom(prenom);
        userService.updateUser(u);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = userService.deleteUserById(id);
        if (!deleted)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    public Response findAll() {
        List<User> list = userService.findAllUser();
        if (list.isEmpty())
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("La base de donnée des users est vide")
                    .build();
        return Response.ok(list).build();
    }
}
