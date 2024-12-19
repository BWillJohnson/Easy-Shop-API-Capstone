package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ICategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements ICategoryDao {

    @Autowired
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String selectSql = " SELECT * FROM categories ";

        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(selectSql);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                categories.add(mapRow(results));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        String searchById = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection connect = getConnection();
             PreparedStatement searchByIdStatement = connect.prepareStatement(searchById)) {

            searchByIdStatement.setInt(1, categoryId);

            try (ResultSet results = searchByIdStatement.executeQuery()) {
                if (results.next()) {
                    return mapRow(results);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Category create(Category category) {
        String createCategoryQuery = "INSERT INTO Categories(name, description ) VALUES (?,?)";

        try (Connection connect = getConnection();
             PreparedStatement createStatement = connect.prepareStatement(createCategoryQuery, Statement.RETURN_GENERATED_KEYS)) {

            createStatement.setString(1, category.getName());
            createStatement.setString(2, category.getDescription());

            int rows = createStatement.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Insert failed, No rows affected!");
            }
            try (ResultSet keyGenerator = createStatement.getGeneratedKeys()) {
                if (keyGenerator.next()) {
                    int generateId = keyGenerator.getInt(1);
                    category.setCategoryId(generateId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }
    @Override
    public void update(int categoryId, Category category) {
        String updateQuery = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
        try (Connection connect = getConnection();
             PreparedStatement updateStatement = connect.prepareStatement(updateQuery)) {

            updateStatement.setString(1, category.getName());
            updateStatement.setString(2, category.getDescription());
            updateStatement.setInt(3, categoryId);

            int rows = updateStatement.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Update failed, no rows affected!");
            }
        } catch (SQLException e) {
            System.err.println("!NOTICE!" + "SQL Error!" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("!NOTICE! Error" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int categoryId) {
        String deletionQuery = "DELETE FROM categories WHERE category_id = ?";

        try (Connection connect = getConnection();
             PreparedStatement deleteStatement = connect.prepareStatement(deletionQuery)) {

            deleteStatement.setInt(1, categoryId);

            int rows = deleteStatement.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Delete failed!, no rows affected!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("!NOTICE! Error" + e.getMessage());
            e.printStackTrace();
        }
    }
    //I am Implementing a Builder Pattern to make my helper method look clearer and concise.
    protected static Category mapRow(ResultSet row) throws SQLException {
        return new Category(row.getInt("category_id"),
                row.getString("name"),
                row.getString("description")
        );
    }
}
