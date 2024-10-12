import React, { useContext } from "react";
import { Link } from 'react-router-dom';
import { AuthContext } from "../context/AuthContext";
import Logout from "./Logout";

const Header = () => {
  const { auth } = useContext(AuthContext);

  return (
    <header className="App-header">
      <div>
        <h1><Link to="/" className="header-title">ECサイト</Link></h1>
      </div>
      <div>
        {auth && (
          <>
            <Link to="/cart" className="cart-button">カート</Link>
            <Logout />
          </>
        )}
      </div>
    </header>
  );
};

export default Header;
