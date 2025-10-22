import React, { useState } from "react";

export default function CheckBalance() {
  const [account, setAccount] = useState("");
  const [balance, setBalance] = useState(null);

  const handleCheck = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch(
        `http://localhost:8080/bank-simulator/api/transactions/balance/${account}`
      );

      if (!res.ok) {
        const errText = await res.text();
        throw new Error(errText || "Unable to fetch balance");
      }

      const result = await res.json();
      setBalance(result);
    } catch (err) {
      alert( err.message);
      setBalance(null);
    }
  };

  return (
    <div className="form-container">
      <h2>Check Balance</h2>
      <form onSubmit={handleCheck}>
        <input
          type="text"
          placeholder="Account Number"
          value={account}
          onChange={(e) => setAccount(e.target.value)}
          required
        />
        <button type="submit">Check</button>
      </form>

      {balance !== null && (
        <div className="result">
          <h3>Current Balance: ₹{balance}</h3>
        </div>
      )}
    </div>
  );
}
