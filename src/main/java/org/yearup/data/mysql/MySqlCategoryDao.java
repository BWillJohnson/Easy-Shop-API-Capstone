package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String selectSql = " SELECT * FROM categories ";

        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(selectSql);
             ResultSet results = statement.executeQuery()) {
            while (results.next()){
                int categoryId = results.getInt("category_id");
                String categoryName = results.getString("name");
                String description = results.getString("description");
                Category category = new Category(categoryId,categoryName,description);
                categories.add(category);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        String searchById = "SELECT category_id, name FROM categories WHERE category_id = ?";
        try (Connection connect = getConnection();
        PreparedStatement statement = connect.prepareStatement(searchById, Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1,categoryId);
            try (ResultSet result = statement.executeQuery()){
                if (result.next()){
                    int categoryIdFromDb = result.getInt("category_id");
                    String categoryName = result.getString("name");
                    String description = result.getString("description");
                    Category category = new Category(categoryIdFromDb,categoryName,description);
                    return category;
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category create(Category category)
    {
        String createCategory = "INSERT INTO Category(name, description )"
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
