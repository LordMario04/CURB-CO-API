package com.curbco.api;

import com.curbco.api.model.Product;
import com.curbco.api.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void save(String brand, String title, long pesos,
                      String category, String tag, String image) {
        String price = "$" + String.format(Locale.US, "%,d", pesos).replace(',', '.');
        productRepository.save(new Product(brand, title, price, pesos * 100, category, tag, image));
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            save("Thrasher", "Mag Logo Hoodie", 89900, "Ropa", "Hot",
                    "https://i.ebayimg.com/images/g/7A4AAOSwcOZlrhMW/s-l1200.jpg");
            save("Vans", "Old Skool Pro", 129900, "Zapatillas", "Sold Out",
                    "https://cdn-images.farfetch-contents.com/15/94/58/48/15945848_44299334_1000.jpg");
            save("Santa Cruz", "Screaming Hand Tee", 45900, "Ropa", "Sale",
                    "https://img.nhs-info.com/heroes/santa_cruz_skateboards_screaming_hand_mobile_1699999705.jpg");
            save("Volcom", "Full Stone Cap", 38900, "Accesorios", null,
                    "https://hatstore.b-cdn.net/198433254654_AI_ENVIRONMENT_1.jpg?width=307&aspect_ratio=307%3A246&quality=80");
            save("HUF", "Essentials TT Hoodie", 75900, "Ropa", null, null);
            save("DC Shoes", "Pure High Top", 110900, "Zapatillas", null, null);
            save("Independent", "Bar Logo Tee", 42900, "Ropa", null, null);
            save("Brixton", "Oath Snapback", 55900, "Accesorios", null, null);
            System.out.println("Productos cargados en la base de datos");
        }
    }
}