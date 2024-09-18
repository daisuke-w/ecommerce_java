import React, { useEffect, useState } from "react";
import axios from "axios";

const ProductList = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    axios.get(`/api/products`)
        .then(response => {
          setProducts(response.data);
        })
        .catch(error => {
          console.error('Error fetching products:', error);
        });
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
