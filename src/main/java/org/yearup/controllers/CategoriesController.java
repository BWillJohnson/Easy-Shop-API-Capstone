package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ICategoryDao;
import org.yearup.data.IProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;



import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController {

    private final ICategoryDao categoryDao;

    private final IProductDao productDao;
     @Autowired
    public CategoriesController(ICategoryDao categoryDao, IProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

   @GetMapping("")
   @PreAuthorize("permitAll()")
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
        Category category = null;
        try {
            category = categoryDao.getById(id);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Notice!... our mistake!");
        }
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Notice!... our mistake!");

        }
        return category;

    }

    @GetMapping("{categoryId}/products")
    @PreAuthorize("permitAll()")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        try {
            List<Product> products = productDao.listByCategoryId(categoryId);

            // If no products are found!
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
        try {
           return  categoryDao.create(category);
        } catch(Exception ex) {
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
    // This bug made was made by me it took me two days to re-think about my logic. ex- I did categoryDao.create(id)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id)
    {
        try {
            var product = categoryDao.getById(id);
            if (product == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            categoryDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
