import React, { useEffect, useState, useContext } from "react";
import axios from '../../services/axiosConfig';
import { Link } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { addToCart } from '../../services/cartService';
import Button from '../Common/Button'

import styles from "./ProductList.module.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState('');
  const { auth } = useContext(AuthContext);

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

  return (
    <div>
      <h1>商品一覧</h1>
      {error && <p className={styles.error}>{error}</p>}
      <div className={styles.productList}>
          {products.length > 0 ? (
              products.map(product => (
                  <div key={product.id} className={styles.productItem}>
                      <h2>{product.name}</h2>
                      <p>{product.description}</p>
                      <p>価格: {product.price}円</p>
                      <Link to={`/product/${product.id}`}>詳細を見る</Link>
                      {auth && (
                        <Button onClick={() => addToCart(product.id)} className="addToCartButton">
                          カートへ追加
                        </Button>
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
