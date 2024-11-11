import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import Button from '../common/Button'

const Logout = () => {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  /** ログアウト処理 */
  const handleLogout = () => {
    // フロントエンド側ログアウト処理呼び出し
    logout();
    navigate('/login');
  };

  return (
    <Button onClick={handleLogout} className="logoutButton">
      ログアウト
    </Button>
  );
};

export default Logout;
