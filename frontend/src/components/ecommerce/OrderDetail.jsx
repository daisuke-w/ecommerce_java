import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getOrderById } from '../../services/orderService';

import styles from "./Order.module.css";

const OrderDetail = () => {
  const { id } = useParams();
  const [order, setOrder] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchOrder = async () => {
      try {
        const data = await getOrderById(id);
        setOrder(data);
      } catch (error) {
        console.error('Error fetching order details:', error);
        setError('注文情報の取得に失敗しました');
      }
    };
    fetchOrder();
  }, [id]);

  // 注文日をフォーマットする関数
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleString("ja-JP", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
    });
  };

  return (
    <div className={styles.orderContainer}>
      <h1>注文詳細</h1>
      {error && <p>{error}</p>}
      {order ? (
        <div>
          <table className={styles.orderDetailsTable}>
            <thead>
              <tr>
                <th>注文ID</th>
                <th>合計金額</th>
                <th>注文日</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{order.id}</td>
                <td>{order.totalAmount}円</td>
                <td>{formatDate(order.orderDate)}</td>
              </tr>
            </tbody>
          </table>

          <h2>商品一覧</h2>
          <table className={styles.orderItemsTable}>
            <thead>
              <tr>
                <th>商品名</th>
                <th>数量</th>
                <th>価格</th>
              </tr>
            </thead>
            <tbody>
              {order.orderItems.map((item) => (
                <tr key={item.id}>
                  <td>{item.product.name}</td>
                  <td>{item.quantity} 個</td>
                  <td>{item.price}円</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <p>注文情報を読み込み中...</p>
      )}
    </div>
  );
};

export default OrderDetail;
