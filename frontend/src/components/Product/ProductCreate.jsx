import React from 'react';
import { useNavigate } from 'react-router-dom';
import { createProduct } from '../../services/productService';
import ProductForm from './ProductForm';

const ProductCreate = () => {
  const navigate = useNavigate();

  const handleCreate = async (productData) => {
    await createProduct(productData);
    navigate('/');
  };

  return (
    <div>
      <h1>商品を出品</h1>
      <ProductForm onSubmit={handleCreate} buttonText="出品する" />
    </div>
  );
};

export default ProductCreate;
