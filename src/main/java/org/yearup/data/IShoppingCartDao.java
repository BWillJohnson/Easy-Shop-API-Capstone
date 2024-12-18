package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import java.util.List;

public interface IShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    List<ShoppingCart> wholeCartList(int productId,int quantity);
    ShoppingCart addToCart(int productId);
    void updateCart(int userId, ShoppingCart cart);
    void deleteCart(int userId);
}
