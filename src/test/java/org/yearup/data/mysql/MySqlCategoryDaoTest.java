package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Category;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest extends BaseDaoTestClass {
    private MySqlCategoryDao dao;

    @BeforeEach
    public void setup(){
        dao = new MySqlCategoryDao(dataSource);
    }
    //Arrange
    @Test
    public void getById_shouldReturn_correctCategory(){
        int category_id = 1;

        Category expected = new Category(){{
            setCategoryId(2);
            setName("Fashion");
            setDescription("Discover trendy clothing and accessories for men and women.");
        }};
        //Act
        var realTimeCategory = dao.getById(category_id);

        //Assert
        assertEquals(expected.getName(),realTimeCategory.getName(),"I tried to get the category of 2 from the Database.");
    }
}