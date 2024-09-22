import React, { useEffect, useState } from "react";
import axios from "axios";
import './Cart.css';

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (!userId) {
      console.error('ユーザーがログインしていません');
      return;
    }

    const fetchCartItems = async () => {
      try {
        const response = await axios.get(`/api/cart`, {
          params: { userId }
        });
        setCartItems(response.data);
        calculateTotal(response.data);
      } catch (error) {
        console.error('カートデータの取得に失敗しました:', error);
      }
    };

    fetchCartItems();
  }, [userId]);

  const calculateTotal = (items) => {
    const total = items.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
    setTotalPrice(total);
  };

  const handleRemove = async (productId) => {
    try {
        await axios.delete('/api/cart/remove', {
            params: { userId, productId }
        });
        setCartItems(cartItems.filter(item => item.product.id !== productId));
        calculateTotal(cartItems.filter(item => item.product.id !== productId));
    } catch (error) {
        console.error('商品の削除に失敗しました:', error);
    }
  };

  const handleQuantityChange = async (productId, newQuantity) => {
    if (newQuantity <= 0) {
        handleRemove(productId);
        return;
    }
    try {
        await axios.put('/api/cart/update', null, {
            params: { userId, productId, quantity: newQuantity }
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

  return (
    <div className="cart">
        <h2>ショッピングカート</h2>
        {cartItems.length === 0 ? (
            <p>カートに商品がありません</p>
        ) : (
            <div>
                <ul>
                    {cartItems.map(item => (
                        <li key={item.product.id}>
                            <div className="cart-item">
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
                                <button onClick={() => handleRemove(item.product.id)}>削除</button>
                            </div>
                        </li>
                    ))}
                </ul>
                <h3>合計金額: ¥{totalPrice}</h3>
            </div>
        )}
    </div>
  );
};

export default Cart;
