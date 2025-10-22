import React from "react";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const username = localStorage.getItem("loggedInUser");
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("loggedInUser");
    navigate("/");
  };

  return (
    <div className="dashboard-container">
      <h2>Welcome, {username}</h2>
      <p>Select an option to continue</p>

      <div className="dashboard-buttons">
        <button onClick={() => navigate("/deposit")}>Deposit</button>
        <button onClick={() => navigate("/withdraw")}>Withdraw</button>
        <button onClick={() => navigate("/transfer")}>Transfer</button>
        <button onClick={() => navigate("/check-balance")}>Check Balance</button>
       <button onClick={() => navigate("/transaction-history")}>Transaction History</button>

        <button onClick={() => navigate("/update-profile")}>Update Profile</button>
         <button onClick={() => navigate("/create-account")}>Create Account</button>
        <button onClick={() => navigate("/update-account")}>Update Account</button>
       <button onClick={() => navigate("/delete-account")}>Delete Account</button>
      </div>

      <button className="logout" onClick={handleLogout}>Logout</button>
    </div>
  );
}
