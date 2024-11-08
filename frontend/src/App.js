import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProductList from './components/ecommerce/ProductList';
import ProductDetail from './components/ecommerce/ProductDetail';
import Cart from './components/ecommerce/Cart';
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import ProtectedRoute from './components/common/ProtectedRoute';
import Header from './components/common/Header';
import ProductCreate from './components/ecommerce/ProductCreate';
import ProductEdit from './components/ecommerce/ProductEdit';
import OrderDetail from './components/ecommerce/OrderDetail';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Routes>
          <Route path="/" element={<ProtectedRoute><ProductList /></ProtectedRoute>} />
          <Route path="/product" element={<ProtectedRoute><ProductCreate /></ProtectedRoute>} />
          <Route path="/product/edit/:id" element={<ProtectedRoute><ProductEdit /></ProtectedRoute>} />
          <Route path="/product/:id" element={<ProtectedRoute><ProductDetail /></ProtectedRoute>} />
          <Route path="/cart" element={<ProtectedRoute><Cart /></ProtectedRoute>} />
          <Route path="/order/:id" element={<ProtectedRoute><OrderDetail /></ProtectedRoute>} />

          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
