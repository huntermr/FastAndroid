package com.hunter.fastandroid.model.interfaces;

import com.hunter.fastandroid.listener.TransactionListener;

/**
 * 购物车模型接口
 */
public interface ICartModel {

    /**
     * 获得用户购物车商品列表
     *
     * @param listener
     */
    void getCartList(TransactionListener listener);

    /**
     * 更新购物车指定商品数量
     *
     * @param cartId   购物车商品ID
     * @param count    新的数量
     * @param listener
     */
    void updateCartCountById(long cartId, int count, TransactionListener listener);
}
