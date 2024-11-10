import React, { useEffect, useState, useContext } from "react";
import axios from '../../services/axiosConfig';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { addToCart } from '../../services/cartService';
import { deleteProduct } from '../../services/productService';
import Button from '../common/Button'

import styles from "./Product.module.css";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState('');
  const { auth, checkUserId } = useContext(AuthContext);
  const navigate = useNavigate();

  /**
   * 非同期で商品一覧を取得する処理
   */
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

  const handleDelete = async (id) => {
    try {
      await deleteProduct(id);
      // 状態を更新してリストから削除
      setProducts(products.filter(product => product.id !== id));
    } catch (error) {
      console.error('Error deleting product:', error);
    }
  };

  return (
    <div>
      <h1>商品一覧</h1>
      {error && <p className={styles.error}>{error}</p>}
      <div className={styles.productList}>
          {products.length > 0 ? (
              products.map(product => (
                  <div key={product.id} className={styles.productItem}>
                    {product.stock === 0 && <div className={styles.soldOut}>Sold Out</div>}
                      <h2>{product.name}</h2>
                      <p>{product.description}</p>
                      <p>価格: {product.price}円</p>
                      <Link to={`/product/${product.id}`}>詳細を見る</Link>
                      {auth && (
                        <>
                          {checkUserId(product.user_id) ? (
                            <>
                              <Button onClick={() => navigate(`/product/edit/${product.id}`)} className="editButton">
                                編集
                              </Button>
                              <Button onClick={() => handleDelete(product.id)} className="deleteButton">
                                削除
                              </Button>
                            </>
                          ) : (
                            <Button
                              onClick={() => addToCart(product.id)}
                              disabled={product.stock === 0}
                              className="addToCartButton"
                            >
                              追加
                            </Button>
                          )}
                        </>
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
