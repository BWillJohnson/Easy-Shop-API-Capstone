package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.yearup.data.IShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

//@Component
//public class ShoppingCartDao extends MySqlDaoBase implements IShoppingCartDao {
//private Map<Integer, ShoppingCartItem> items
//
//
//    @Override
//    public ShoppingCart getByUserId(int userId) {
//        return null;
//    }
//
//    @Override
//    public List<ShoppingCart> wholeCartList(int productId, int quantity) {
//        return null;
//    }
//
//    @Override
//    public ShoppingCart addToCart(int productId) {
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
