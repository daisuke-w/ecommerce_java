import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from '../../context/AuthContext';

/**
 * ルーティング定義
 * @param children 子コンポーネント 
 * @returns 各ページ
 */
const ProtectedRoute = ({ children }) => {
  const { auth, loading } = useContext(AuthContext);

  // ローディング中はローディング表示
  if (loading) {
    return <p>Loading...</p>;
  }

  // 認証されていない場合、ログインページへリダイレクト
  if (!auth) {
    return <Navigate to="/login" replace />
  }

  // 認証されている場合、子コンポーネントをレンダリング
  return children;
};

export default ProtectedRoute;
