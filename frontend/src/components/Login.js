import React, { useState, useContext } from "react";
import axios from '../services/axiosConfig';
import { useNavigate, Link } from "react-router-dom";
import { AuthContext } from '../context/AuthContext';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/users/login', { username, password });

      const { token, userId } = response.data.token;

      if (token) {
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
    <div className="form-container">
      <h2 className="sign-title">ログイン</h2>
      {error && <p>{error}</p>}
      <form onSubmit={handleLogin} className="sign-form">
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
        <button type="submit" className="submit-button">ログイン</button>
      </form>
      <p>アカウントをお持ちでない場合は<Link to="/signup">こちら</Link></p>
    </div>
  );
};

export default Login;
