import React, { useEffect, useState, useContext } from "react";
import axios from '../services/axiosConfig';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

import "./ProductList.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState('');
  const { auth, userId } = useContext(AuthContext);

  useEffect(() => {
    const fetchProducts = async () => {
    try {
      const response = await axios.get('/products');
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
      setError('商品を取得できませんでした。');
    }
    };
  
    fetchProducts();
  }, []);

  const addToCart = async (productId) => {
    try {
      await axios.post('/cart/add', {
        userId: userId,
        productId: productId,
        quantity: 1
      });
      alert('カートに追加しました！');
    } catch (error) {
      console.error('Error adding to cart:', error);
      alert('カートに追加できませんでした。');
    }
  };

  return (
    <div>
      <h1>商品一覧</h1>
      {error && <p className="error">{error}</p>}
      <div className="product-list">
          {products.length > 0 ? (
              products.map(product => (
                  <div key={product.id} className="product-item">
                      <h2>{product.name}</h2>
                      <p>{product.description}</p>
                      <p>価格: {product.price}円</p>
                      <Link to={`/product/${product.id}`}>詳細を見る</Link>
                      {auth && (
                        <button onClick={() => addToCart(product.id)} className="add-to-cart-button">
                          カートへ追加
                        </button>
                      )}
                  </div>
              ))
          ) : (
            <p>商品がありません。</p>
          )}
      </div>
    </div>
  );
};

export default ProductList;
