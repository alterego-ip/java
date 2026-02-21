package com.example.techshop.resources;

import com.example.techshop.Product;
import com.example.techshop.ProductDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private final ProductDAO dao = new ProductDAO();

    @GET
    public List<Product> listProducts() throws SQLException {
        return dao.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") int id) throws SQLException {
        Product p = dao.getById(id);
        if (p != null) {
            return Response.ok(p).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Товар не знайдено\"}").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) throws SQLException {
        dao.add(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id, Product productPatch) throws SQLException {
        Product existing = dao.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Товар з таким ID не знайдено\"}").build();
        }

        String newName = (productPatch.getName() != null) ? productPatch.getName() : existing.getName();
        String newBrand = (productPatch.getBrand() != null) ? productPatch.getBrand() : existing.getBrand();
        String newCategory = (productPatch.getCategory() != null) ? productPatch.getCategory() : existing.getCategory();
        
        int newQuantity = (productPatch.getQuantity() != 0) ? productPatch.getQuantity() : existing.getQuantity();
        double newPrice = (productPatch.getPrice() != 0) ? productPatch.getPrice() : existing.getPrice();

        Product updatedProduct = new Product(id, newName, newBrand, newCategory, newQuantity, newPrice);

        dao.update(updatedProduct);

        return Response.ok(updatedProduct).build();
    }
}