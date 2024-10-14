import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from '../../context/AuthContext';

const ProtectedRoute = ({ children }) => {
  const { auth, loading } = useContext(AuthContext);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (!auth) {
    return <Navigate to="/login" replace />
  }

  return children;
};

export default ProtectedRoute;