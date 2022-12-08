package com.example.springsecurityapplication.controllers;
import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class MainController {
    private final ProductRepository productRepository;

    private final ProductService productService;

    @Autowired
    public MainController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    // Данный метод предназначен для отображения товаров без аутентификации и авторизации
    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "product/product";
    }
    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }
    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model){
        // Если диапазон цен от и до не пустой
        if(!from.isEmpty() & !to.isEmpty()){
            // Если сортировка по цене выбрана
            if(!price.isEmpty()){
                // Если в качестве сортировки выбрана сортировка по возрастанию
                if(price.equals("sorted_by_ascending_price"))
                {
                    // Если категория товара не пустая
                    if(!category.isEmpty())
                    {
                        // Если категория равна мебели
                        if(category.equals("furniture")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                            // Если категория равна бытовой технике
                        } else if(category.equals("appliances")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                            // Если категория равна одежде
                        } else if(category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        }
                        // Если категория не выбрана
                    } else {
                        model.addAttribute("search_product", productRepository.findByTitleAndOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                    // Если в качестве сортировки выбрана сортировка по убыванию
                } else if(price.equals("sorted_by_descending_price")){
                    if(!category.isEmpty())
                    {
                        if(category.equals("furniture")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        } else if(category.equals("appliances")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        } else if(category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        }
                    } else {
                        model.addAttribute("search_product", productRepository.findByTitleAndOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                }
            } else {
                    model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                }
        } else {
                model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
            }
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_from", from);
        model.addAttribute("value_price_to", to);
        model.addAttribute("products", productService.getAllProduct());

        return "/product/product";
    }
}
