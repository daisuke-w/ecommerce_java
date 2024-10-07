import React, { useEffect, useState, useContext } from "react";
import { useParams } from 'react-router-dom';
import axios from '../services/axiosConfig';
import { AuthContext } from '../context/AuthContext';

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const { auth, userId } = useContext(AuthContext);

  useEffect(() => {
    axios.get(`/products/${id}`)
        .then(response => {
          setProduct(response.data);
        })
        .catch(error => {
          console.error('Error fetching product details:', error);
        });
  }, [id]);

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

  if (!product) {
    return <p>商品情報を読み込んでいます...</p>
  }

  return (
    <div>
      <h1>{product.name}</h1>
      <p>{product.description}</p>
      <p>価格: {product.price}円</p>
      <p>在庫: {product.stock}</p>
      {auth && (
        <button onClick={addToCart} className="add-to-cart-button">
          カートへ追加
        </button>
      )}
    </div>
  );
};

export default ProductDetail;
