package org.yearup.controllers;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
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
    public List<Category> getAll()
    {
        try {
            List<Category> categories = categoryDao.getAllCategories();
            return categories;

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Notice!... our mistake!");
        }
    }

   @GetMapping("{id}")
   @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id)
    {
        try {
            return categoryDao.getById(id);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Notice!... our mistake!");
        }

    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    @PreAuthorize("permitAll()")

    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        try {
            List<Product> products = productDao.listByCategoryId(categoryId);

            // If no products are found, throw 404 (Not Found)
            if (products.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for category ID " + categoryId);
            }

            return products;
        } catch (Exception e) {
            // Log the exception (using a logger in real-world code)
            System.err.println("Error fetching products for category " + categoryId + ": " + e.getMessage());

            // Return a 500 Internal Server Error for unexpected issues
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Notice!... our mistake!", e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category)
    {
        try
        {
            return categoryDao.create(category);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Notice!... our mistake!");
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        try {
            categoryDao.update(id,category);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Notice!... our mistake!");

        }

    }
    // Projected URL: https://localhost:8080/categories/{id}
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id)
    {
        try
        {
            var category =  categoryDao.getById(id);

            if(category == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.delete(id);
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad!");
        }
    }
}
