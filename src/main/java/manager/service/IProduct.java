package manager.service;

import manager.model.Product;
import manager.service.my_interface.ICrud;
import manager.service.my_interface.ISearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProduct extends ICrud<Product>, ISearch<Product> {
    Page<Product> findAllPag(int pageNumber , int pageSize);
    Page<Product> findByNamePag( String name, int pageNumber , int pageSize);
}
