import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getOrderById } from '../../services/orderService';

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

  return (
    <div>
      <h1>注文詳細</h1>
      {error && <p>{error}</p>}
      {order ? (
        <div>
          <p>注文ID: {order.id}</p>
          <p>合計金額: {order.totalAmount}円</p>
          <p>注文日: {order.orderDate}</p>
          <h2>商品一覧</h2>
          <ul>
            {order.orderItems.map(item => (
              <li key={item.id}>
                {item.product.name} - {item.quantity} 個 ({item.price}円)
              </li>
            ))}
          </ul>
        </div>
      ) : (
        <p>注文情報を読み込み中...</p>
      )}
    </div>
  );
};

export default OrderDetail;
