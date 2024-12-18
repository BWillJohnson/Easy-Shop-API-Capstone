package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.yearup.data.IShoppingCartDao;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.util.List;
//@Component
//public class ShoppingCartDao extends MySqlDaoBase implements IShoppingCartDao {
//    @Autowired
//    public ShoppingCartDao(DataSource dataSource) {
//        super(dataSource);
//    }
//
//    @Override
//    public ShoppingCart getByUserId(int userId) {
//        return null;
//    }
//
//    @Override
//    public List<ShoppingCart> wholeCartList() {
//        return null;
//    }
//
//    @Override
//    public ShoppingCart addToCart(ShoppingCart cart) {
//        return null;
//    }
//
//    @Override
//    public void updateCart(int userId, ShoppingCart cart) {
//
//    }
//
//    @Override
//    public void deleteCart(int userId) {
//
//    }
//}
