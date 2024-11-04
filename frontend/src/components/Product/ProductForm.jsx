import React, { useState, useEffect } from 'react';
import Button from '../Common/Button';
import styles from './ProductForm.module.css';

const ProductForm = ({ initialData = {}, onSubmit, buttonText }) => {
  const [name, setName] = useState(initialData.name || '');
  const [description, setDescription] = useState(initialData.description || '');
  const [price, setPrice] = useState(initialData.price || '');
  const [stock, setStock] = useState(initialData.stock || '');

  useEffect(() => {
    if (initialData) {
      setName(initialData.name || '');
      setDescription(initialData.description || '');
      setPrice(initialData.price || '');
      setStock(initialData.stock || '');
    }
  }, [initialData]);

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({ name, description, price: parseFloat(price), stock });
  };

  return (
    <form onSubmit={handleSubmit} className={styles.productForm}>
      {/* 各フィールド */}
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
      <Button type="submit">{buttonText}</Button>
    </form>
  );
};

export default ProductForm;
