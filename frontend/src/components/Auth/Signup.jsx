import React, { useState, useContext } from 'react';
import axios from '../../services/axiosConfig';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import Button from '../common/Button'

import styles from "./Auth.module.css";

const Signup = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  /** サインアップ処理 */
  const handleSignup = async (e) => {
    e.preventDefault();

    try {
      // 非同期でサインアップ処理呼び出し
      const response = await axios.post('/users/register', { username, password });
      const { token, userId } = response.data;
      if (token) {
        login(token, userId);
        navigate('/');
      }
    } catch (err) {
      setError('サインアップに失敗しました。');
    }
  };

  return (
    <div className={styles.formContainer}>
      <h2 className={styles.signTitle}>サインアップ</h2>
      {error && <p>{error}</p>}
      <form onSubmit={handleSignup} className={styles.signForm}>
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
        <Button type="submit" className="submitButton">サインアップ</Button>
      </form>
      <p>ログインページは<Link to="/login">こちら</Link></p>
    </div>
  );
};

export default Signup;
