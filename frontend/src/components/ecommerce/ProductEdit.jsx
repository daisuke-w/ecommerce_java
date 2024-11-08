import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProductById, updateProduct } from '../../services/productService';
import ProductForm from './ProductForm';

const ProductEdit = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [productData, setProductData] = useState(null);

  useEffect(() => {
    const fetchProduct = async () => {
      const data = await getProductById(id);
      setProductData(data);
    };
    fetchProduct();
  }, [id]);

  const handleUpdate = async (updatedData) => {
    await updateProduct(id, updatedData);
    navigate('/');
  };

  return (
    <div>
      <h1>商品を編集</h1>
      {productData && (
        <ProductForm initialData={productData} onSubmit={handleUpdate} buttonText="更新する" />
      )}
    </div>
  );
};

export default ProductEdit;
