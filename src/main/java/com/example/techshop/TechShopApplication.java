package com.example.techshop;

import com.example.techshop.resources.ProductResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.core.Configuration;

public class TechShopApplication extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new TechShopApplication().run(new String[]{"server"});
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        environment.getObjectMapper().enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
        environment.jersey().register(new ProductResource());
    }
}