package com.simulator.bank.api;

import com.simulator.bank.dao.CustomerDao;
import com.simulator.bank.model.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.Map;  

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final CustomerDao customerDao = new CustomerDao();

    @POST
    @Path("/login")
    public Response login(Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            if (username == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Username and password are required"))
                        .build();
            }

            Customer c = customerDao.findByUsername(username);
            if (c == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "User not found"))
                        .build();
            }

            if (!password.equals(c.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "Invalid password"))
                        .build();
            }

      
            c.setPassword(null);
            return Response.ok(c).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Database error"))
                    .build();
        }
    }
}
