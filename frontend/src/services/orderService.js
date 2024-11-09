import axios from './axiosConfig';

/**
 * 注文を作成する
 * @returns 作成された注文情報
 */
export const createOrder = async () => {
  try {
    const response = await axios.post('/orders');
    return response.data;
  } catch (error) {
    console.error('Error creating order:', error);
    throw error;
  }
};

/**
 * 注文をIDで取得する
 * @param orderId - 注文のID
 * @returns 注文情報
 */
export const getOrderById = async (orderId) => {
  try {
    const response = await axios.get(`/orders/${orderId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching order:', error);
    throw error;
  }
};
