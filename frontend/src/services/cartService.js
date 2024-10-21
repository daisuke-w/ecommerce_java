import axios from './axiosConfig';

/**
 * カートに商品を追加する処理
 * @param productId 商品Id
 * @param quantity 商品数
 */
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
