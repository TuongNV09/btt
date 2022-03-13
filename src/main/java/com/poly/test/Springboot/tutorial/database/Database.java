package com.poly.test.Springboot.tutorial.database;

import com.poly.test.Springboot.tutorial.models.Product;
import com.poly.test.Springboot.tutorial.reponsitories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import java.util.logging.Logger;

@Configuration  // Chứa các bean khởi tạo database, biến môi trường
public class Database {
    // logger tương đương với System.out.println
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){
      //CommandLineRunner là 1 interfaqce
        return new CommandLineRunner() {
            // Tạo mới một đối tượng để thực thi interface CommandLineRunner
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product("Apple", 2021, 248.0, "");
                Product productB = new Product("Android", 2021, 218.0, "");
                logger.info("insert data: " + productRepository.save(productA)); // Hàm save lưu bản ghi vào trong database
                logger.info("insert data: " + productRepository.save(productB));
            }
        };
    }
}
