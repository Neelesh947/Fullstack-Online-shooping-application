package in.neelesh.online.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.ProductImage;
import jakarta.transaction.Transactional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

	@Modifying
    @Transactional
    @Query("DELETE FROM ProductImage pi WHERE pi.product.id = :productId")
    void deleteByProductId(String productId);
}
