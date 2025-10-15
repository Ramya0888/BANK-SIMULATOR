package com.simulator.bank.api;

import com.simulator.bank.dao.CustomerDao;
import com.simulator.bank.model.Customer;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerDao dao = new CustomerDao();

    // Get all customers
    @GET
    public Response getAll() {
        try {
            List<Customer> list = dao.findAll();
            return Response.ok(list).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    //  Get by ID
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        try {
            Customer c = dao.findById(id);
            if (c == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found for ID: " + id)
                        .build();
            return Response.ok(c).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Create new customer
    @POST
    public Response create(Customer c) {
        try {
            int newId = dao.create(c);
            if (newId > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("Customer created with ID: " + newId)
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to create customer.")
                        .build();
            }
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Update existing customer
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Customer c) {
        try {
            boolean updated = dao.update(id, c);
            if (updated)
                return Response.ok("Customer updated successfully.").build();
            else
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found for update.")
                        .build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    //  Delete customer
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            boolean deleted = dao.delete(id);
            if (deleted)
                return Response.ok("Customer deleted successfully.").build();
            else
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found for deletion.")
                        .build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
