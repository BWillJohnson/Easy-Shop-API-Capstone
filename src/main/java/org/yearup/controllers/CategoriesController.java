package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/categories")
public class CategoriesController {

    private final CategoryDao categoryDao;

    private final ProductDao productDao;
     @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

   @PreAuthorize("permitAll()")
   @GetMapping("")
   @RequestMapping(path = "/")
    public List<Category> getAll()
    {
        List<Category> categories = categoryDao.getAllCategories();
        return categories;
    }

   @RequestMapping(path = "/{categoryId}",method = RequestMethod.GET)
    public Category getById(@PathVariable int id)
    {
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    @RequestMapping(path = "{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
      List<Product>products = productDao.listByCategoryId(categoryId);
        return products;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin")
    public Category addCategory(@RequestBody Category category)
    {

        return categoryDao.getById(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin")
    public void deleteCategory(@PathVariable int id)
    {
        // delete the category by id
    }
}
