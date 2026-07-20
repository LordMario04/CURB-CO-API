package com.curbco.api;

import com.curbco.api.model.Product;
import com.curbco.api.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            productRepository.save(new Product("Thrasher", "Mag Logo Hoodie", "$89.900", "Ropa", "Hot"));
            productRepository.save(new Product("Vans", "Old Skool Pro", "$129.900", "Zapatillas", null));
            productRepository.save(new Product("Santa Cruz", "Screaming Hand Tee", "$45.900", "Ropa", "Sale"));
            productRepository.save(new Product("Volcom", "Full Stone Cap", "$38.900", "Accesorios", null));
            productRepository.save(new Product("HUF", "Essentials TT Hoodie", "$75.900", "Ropa", null));
            productRepository.save(new Product("DC Shoes", "Pure High Top", "$110.900", "Zapatillas", null));
            productRepository.save(new Product("Independent", "Bar Logo Tee", "$42.900", "Ropa", null));
            productRepository.save(new Product("Brixton", "Oath Snapback", "$55.900", "Accesorios", null));
            System.out.println("Productos cargados en la base de datos");
        }
    }
}