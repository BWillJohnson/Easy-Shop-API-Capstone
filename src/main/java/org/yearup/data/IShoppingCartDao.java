package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface IShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
}
