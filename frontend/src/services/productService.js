import axios from './axiosConfig';

/**
 * 商品を出品する
 * @param productData 出品する商品情報
 * @returns 出品した商品情報
 */
export const createProduct = async (productData) => {
  try {
    const response = await axios.post('/products', productData);
    return response.data;
  } catch (error) {
    console.error('Error creating product:', error);
    throw error;
  }
};

/**
 * 商品を更新する
 * @param id 更新する商品のID
 * @param productData 更新する商品情報
 * @returns 更新された商品情報
 */
export const updateProduct = async (id, productData) => {
  try {
    const response = await axios.put(`/products/${id}`, productData);
    return response.data;
  } catch (error) {
    console.error('Error updating product:', error);
    throw error;
  }
};

/**
 * 商品を削除する
 * @param id 削除する商品のID
 */
export const deleteProduct = async (id) => {
  try {
    await axios.delete(`/products/${id}`);
  } catch (error) {
    console.error('Error deleting product:', error);
    throw error;
  }
};

/**
 * 商品を取得する
 * @param id 商品Id
 * @returns 取得した商品情報
 */
export const getProductById = async (id) => {
  try {
    const response = await axios.get(`/products/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching product by ID:", error);
    throw error;
  }
};
