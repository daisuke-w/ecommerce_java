import React, { useEffect, useState } from "react";
import axios from '../services/axiosConfig';
import { Link } from 'react-router-dom';

import "./ProductList.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState('');

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
      <div className="product-list">
          {products.length > 0 ? (
              products.map(product => (
                  <div key={product.id} className="product-item">
                      <h2>{product.name}</h2>
                      <p>{product.description}</p>
                      <p>価格: {product.price}円</p>
                      <Link to={`/product/${product.id}`}>詳細を見る</Link>
                  </div>
              ))
          ) : (
            error && <p>{error}</p>
          )}
      </div>
    </div>
  );
};

export default ProductList;
