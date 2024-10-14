import React, { useState } from 'react';
import axios from '../../services/axiosConfig';
import { useNavigate, Link } from 'react-router-dom';

const Signup = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();

    try {
      await axios.post('/users/register', { username, password });

      // サインアップ成功後にログインページに遷移
      navigate('/login');
    } catch (err) {
      setError('サインアップに失敗しました。');
    }
  };

  return (
    <div className="form-container">
      <h2 className="sign-title">サインアップ</h2>
      {error && <p>{error}</p>}
      <form onSubmit={handleSignup} className="sign-form">
        <div className="input-item">
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            placeholder="ユーザー名" 
            required 
          />
        </div>
        <div className="input-item">
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            placeholder="パスワード" 
            required 
          />
        </div>
        <button type="submit" className="submit-button">サインアップ</button>
      </form>
      <p>ログインページは<Link to="/login">こちら</Link></p>
    </div>
  );
};

export default Signup;
