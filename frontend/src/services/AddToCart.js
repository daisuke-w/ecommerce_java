import axios from './axiosConfig';

export const addToCart = async (productId, quantity = 1) => {
  try {
    await axios.post('/cart/add', {
      productId: productId,
      quantity: quantity
    });
    alert('カートに追加しました！');
  } catch (error) {
    console.error('Error adding to cart:', error);
    alert('カートに追加できませんでした。');
  }
};
