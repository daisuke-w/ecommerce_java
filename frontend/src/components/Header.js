import React, { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import Logout from "./Logout";

const Header = () => {
  const { auth } = useContext(AuthContext);

  return (
    <header className="App-header">
      <h1>ECサイト</h1>
      <nav>
        {auth && <Logout />}
      </nav>
    </header>
  );
};

export default Header;
