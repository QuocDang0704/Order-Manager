package fis.quocdb3.ordermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import fis.quocdb3.ordermanager.dto.ProductDTO;
import fis.quocdb3.ordermanager.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("select p from Product p")
//    List<ProductDTO> findAllWithNameOnly();

    List<ProductDTO> findAllProjectedBy();

    List<ProductDTO> findAllByName(String productName);

}
