package manager.service.my_impl;

import manager.model.Product;
import manager.repository.IProductRepository;
import manager.service.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements IProduct {
    @Value("${image}")
    private String uploadPath;
    @Autowired
    IProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {
        if (!product.getMultipartFile().isEmpty()) {
            product.setImg(getFileName(product));
        } else {
            product.setImg("Coca-Cola_New.png");
        }
        productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> findByNamePag(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String search="%"+name+"%";
        return productRepository.findByName(search,pageable);
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    private String getFileName(Product product) {
        MultipartFile multipartFile = product.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileName;
    }
    @Override
    public Page<Product> findAllPag(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }
}
