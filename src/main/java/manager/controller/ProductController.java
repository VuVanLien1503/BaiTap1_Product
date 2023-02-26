package manager.controller;

import manager.model.Product;
import manager.service.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
    @Value("${image}")
    private String uploadPath;
    @Autowired
    IProduct productImpl;

    @GetMapping
    public String showListPag(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size) {
        model.addAttribute("product", new Product());
        Page<Product> allPag = productImpl.findAllPag(page, size);
        model.addAttribute("listProduct", allPag.getContent());
        model.addAttribute("totalPages", allPag.getTotalPages());
        model.addAttribute("currentPage", page);
        return "/product/display";
    }

    @PostMapping("/create")
    String create(@ModelAttribute Product product) {
        MultipartFile multipartFile = product.getMultipartFile();
        product.setImg(multipartFile.getOriginalFilename());
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + product.getImg()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        productImpl.save(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/update/{id}")
    ModelAndView showUpdate(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/product/update");
        modelAndView.addObject("product", productImpl.findById(id));
        return modelAndView;
    }

    @PostMapping(value = "/update/{id}")
    String update(@ModelAttribute Product product) {
        productImpl.save(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/delete/{id}")
    String delete(@PathVariable("id") Long id) {
        productImpl.delete(productImpl.findById(id));
        return "redirect:/products";
    }
    @PostMapping  (value = "/search")
    public String showListPag(Model model,
                                    @RequestParam("search")String name,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "3") int size) {
        model.addAttribute("product", new Product());
        Page<Product> allPag = productImpl.findByNamePag(name,page, size);
        model.addAttribute("listProduct", allPag.getContent());
        model.addAttribute("totalPages", allPag.getTotalPages());
        model.addAttribute("currentPage", page);
        return "/product/display";
    }
}
