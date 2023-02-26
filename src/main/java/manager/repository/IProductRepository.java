package manager.repository;

import manager.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface IProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByPriceBetween(Double price, Double price1);

    @Query(value = "select p from Product p where p.name like :name")
    Page<Product> findByName(@Param("name") String name,Pageable pageable);
}
