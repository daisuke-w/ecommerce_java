import React, { useEffect, useState, useContext } from "react";
import { useNavigate } from 'react-router-dom';
import axios from '../../services/axiosConfig';
import { createOrder } from '../../services/orderService';
import { AuthContext } from '../../context/AuthContext';
import Button from '../common/Button'

import styles from './Cart.module.css';

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const { auth } = useContext(AuthContext);
  const navigate = useNavigate();

  /** カート内の商品を非同期で取得する処理 */
  useEffect(() => {
    if (!auth) {
      console.error('ユーザーがログインしていません');
      return;
    }

    const fetchCartItems = async () => {
      try {
        const response = await axios.get(`/cart`);
        setCartItems(response.data);
        calculateTotal(response.data);
      } catch (error) {
        console.error('カートデータの取得に失敗しました:', error);
      }
    };

    fetchCartItems();
  }, [auth]);

  /**
   * カート内の商品の合計金額を算出する処理
   * @param items 商品
   */
  const calculateTotal = (items) => {
    const total = items.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
    setTotalPrice(total);
  };

  /**
   * カート内の商品を削除する処理
   * @param productId 商品Id
   */
  const handleRemove = async (productId) => {
    try {
        await axios.delete('/cart/remove', {
            params: { productId }
        });
        setCartItems(cartItems.filter(item => item.product.id !== productId));
        calculateTotal(cartItems.filter(item => item.product.id !== productId));
    } catch (error) {
        console.error('商品の削除に失敗しました:', error);
    }
  };

  /**
   * カート内の商品を更新する処理
   * @param productId 商品Id
   * @param newQuantity 商品数
   */
  const handleQuantityChange = async (productId, newQuantity) => {
    if (newQuantity <= 0) {
        handleRemove(productId);
        return;
    }
    try {
        await axios.put('/cart/update', null, {
            params: { productId, quantity: newQuantity }
        });
        const updatedCartItems = cartItems.map(item =>
            item.product.id === productId ? { ...item, quantity: newQuantity } : item
        );
        setCartItems(updatedCartItems);
        calculateTotal(updatedCartItems);
    } catch (error) {
        console.error('数量の更新に失敗しました:', error);
    }
  };

  /**
   * 注文作成処理を呼び出す
   */
  const handleOrderCreate = async () => {
    try {
      const order = await createOrder();
      // 注文作成が成功したら、注文詳細ページに遷移する
      navigate(`/order/${order.id}`);
    } catch (error) {
      console.error('注文作成に失敗しました:', error);
      alert('注文作成に失敗しました');
    }
  };

  return (
    <div className={styles.cart}>
        <h2>ショッピングカート</h2>
        {cartItems.length === 0 ? (
            <p>カートに商品がありません</p>
        ) : (
            <div className={styles.cartList}>
                <ul>
                    {cartItems.map(item => (
                        <li key={item.product.id}>
                            <div className={styles.cartItem}>
                                <span>{item.product.name}</span>
                                <span>価格: ¥{item.product.price}</span>
                                <span>
                                    数量: 
                                    <input 
                                        type="number" 
                                        value={item.quantity} 
                                        onChange={(e) => handleQuantityChange(item.product.id, parseInt(e.target.value))}
                                    />
                                </span>
                                <Button onClick={() => handleRemove(item.product.id)} className="deleteButton">削除</Button>
                            </div>
                        </li>
                    ))}
                </ul>
                <h3>合計金額: ¥{totalPrice}</h3>
                <Button onClick={handleOrderCreate} className="submitButton">注文確定</Button>
            </div>
        )}
    </div>
  );
};

export default Cart;
