import React, { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(false);
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState(null);

  /**
   * レンタリング後にログイン状態を判定する処理
   */
  useEffect(() => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    if (token && userId) {
      setAuth(true);
      setUserId(Number(userId));
    }
    // ログイン状態を確認してから値を設定
    setLoading(false);
  }, []);

  /**
   * トークンとUserIdをlocalStorageとStateに設定する処理
   * @param token  ログイン時に生成
   * @param userId ログインしているUserId
  */
  const login = (token, userId) => {
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId);
    setAuth(true);
    setUserId(Number(userId));
  };

  /**
   * トークンとUserIdをlocalStorageとStateから削除する処理
  */
  const logout = () => {
    localStorage.removeItem(`token`);
    localStorage.removeItem('userId');
    setAuth(false);
    setUserId(null);
  };

  /**
   * ログイン中のUserIdと一致するか確認する処理
   * @param otherUserId 比較対象のUserId
   * @returns 真偽値
   */
  const checkUserId = (otherUserId) => {
    if (userId === otherUserId) {
      return true;
    }
    return false;
  };

  return (
    <AuthContext.Provider value={{ auth, loading, userId, login, logout, checkUserId }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
