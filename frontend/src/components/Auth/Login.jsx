import React, { useState, useContext } from "react";
import axios from '../../services/axiosConfig';
import { useNavigate, Link } from "react-router-dom";
import { AuthContext } from '../../context/AuthContext';
import Button from '../common/Button'

import styles from "./Auth.module.css";

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  /** ログイン処理 */
  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      // 非同期でログイン処理呼び出し
      const response = await axios.post('/users/login', { username, password });
      const { token, userId } = response.data;

      if (token) {
        // フロント側ログイン処理呼び出し
        login(token, userId);
        navigate('/');
      } else {
        setError('ログインに失敗しました。トークンが取得できませんでした。');
      }
    } catch (err) {
      setError('ログインに失敗しました。ユーザー名またはパスワードが正しくありません。');
    }
  };

  return (
    <div className={styles.formContainer}>
      <h2 className={styles.signTitle}>ログイン</h2>
      {error && <p>{error}</p>}
      <form onSubmit={handleLogin} className={styles.signForm}>
        <div className={styles.inputItem}>
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            placeholder="ユーザー名" 
            required 
          />
        </div>
        <div className={styles.inputItem}>
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            placeholder="パスワード" 
            required 
          />
        </div>
        <Button type="submit" className="submitButton">ログイン</Button>
      </form>
      <p>アカウントをお持ちでない場合は<Link to="/signup">こちら</Link></p>
    </div>
  );
};

export default Login;
