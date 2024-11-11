import React from "react";
import styles from "./Button.module.css";

const Button = ({ onClick, children, type='button', disabled=false, className }) => {
  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`${styles.button} ${styles[className]}`}
    >
      {children}
    </button>
  );
};

export default Button;
