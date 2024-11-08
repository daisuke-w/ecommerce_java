import React, { useContext } from "react";
import { Link } from 'react-router-dom';
import { AuthContext } from "../../context/AuthContext";
import Logout from "../auth/Logout";

import styles from "./Header.module.css";

const Header = () => {
  const { auth } = useContext(AuthContext);

  return (
    <header className={styles.header}>
      <div>
        <h1><Link to="/" className={styles.headerTitle}>ECサイト</Link></h1>
      </div>
      <div>
        {auth && (
          <>
            <Link to="/cart" className={styles.headerCart}>カート</Link>
            <Link to="/product" className={styles.newProduct}>出品</Link>
            <Logout />
          </>
        )}
      </div>
    </header>
  );
};

export default Header;
