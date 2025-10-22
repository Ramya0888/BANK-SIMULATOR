import React, { useState } from "react";

export default function DeleteAccount() {
  const [accountNumber, setAccountNumber] = useState("");

  const handleDelete = async (e) => {
    e.preventDefault();

    if (!accountNumber) {
      alert("Please enter your account number.");
      return;
    }

    if (!window.confirm("Are you sure you want to delete this account?")) {
      return;
    }

    try {
      const res = await fetch(
        `http://localhost:8080/bank-simulator/api/accounts/by-number/${accountNumber}`,
        { method: "DELETE" }
      );

      if (res.ok) {
        alert("Account deleted successfully!");
        setAccountNumber("");
      } else {
        const errorText = await res.text();
        alert("Failed to delete account: " + errorText);
      }
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  return (
    <div className="form-container">
      <h2>Delete Account</h2>
      <form onSubmit={handleDelete}>
        <input
          type="text"
          placeholder="Enter Account Number"
          value={accountNumber}
          onChange={(e) => setAccountNumber(e.target.value)}
        />
        <button type="submit" style={{ backgroundColor: "red", color: "white" }}>
          Delete Account
        </button>
      </form>
    </div>
  );
}
