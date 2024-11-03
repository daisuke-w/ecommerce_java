import React, { useEffect, useState, useContext } from "react";
import { useParams } from 'react-router-dom';
import axios from '../../services/axiosConfig';
import { AuthContext } from '../../context/AuthContext';
import { addToCart } from '../../services/cartService';
import Button from '../Common/Button'

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const { auth, checkUserId } = useContext(AuthContext);

  /**
   * 非同期で商品詳細を取得する処理
   */
  useEffect(() => {
    axios.get(`/products/${id}`)
        .then(response => {
          setProduct(response.data);
        })
        .catch(error => {
          console.error('Error fetching product details:', error);
        });
  }, [id]);

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
        <>
          {checkUserId(product.user_id) ? (
            <>
              <Button className="editButton">
                編集
              </Button>
              <Button className="deleteButton">
                削除
              </Button>
            </>
          ) : (
            <Button onClick={() => addToCart(product.id)} className="addToCartButton">
              カートへ追加
            </Button>
          )}
        </>
      )}
    </div>
  );
};

export default ProductDetail;
