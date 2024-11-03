import React, { useState } from "react";
import axios from '../../services/axiosConfig';
import { useNavigate } from 'react-router-dom';
import Button from '../Common/Button';

import styles from "./ProductCreate.module.css";

const ProductCreate = () => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [stock, setStock] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  /**
   * 商品の出品を処理する非同期関数
   */
  const handleSubmit = async (event) => {
    event.preventDefault();
    
    try {
      const productData = { name, description, price: parseFloat(price), stock };
      await axios.post('/products', productData);
      setSuccess(true);
      navigate('/');
    } catch (error) {
      console.error('Error creating product:', error);
      setError('商品を登録できませんでした。');
    }
  };

  return (
    <div className={styles.productCreate}>
      <h1>商品を出品</h1>
      {error && <p className={styles.error}>{error}</p>}
      {success && <p className={styles.success}>商品が正常に登録されました。</p>}
      <form onSubmit={handleSubmit}>
        <div className={styles.formGroup}>
          <label htmlFor="name">商品名</label>
          <input 
            type="text" 
            id="name" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            required 
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="description">商品説明</label>
          <textarea 
            id="description" 
            value={description} 
            onChange={(e) => setDescription(e.target.value)} 
            required 
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="price">価格</label>
          <input 
            type="number" 
            id="price" 
            value={price} 
            onChange={(e) => setPrice(e.target.value)} 
            required 
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="stock">在庫数</label>
          <input 
            type="number" 
            id="stock" 
            value={stock} 
            onChange={(e) => setStock(e.target.value)} 
            required 
          />
        </div>
        <Button type="submit" className="submitButton">出品する</Button>
      </form>
    </div>
  );
};

export default ProductCreate;
