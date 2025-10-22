import React, { useState } from "react";
//console.log(" Deposit component loaded");

export default function Deposit() {
  const [account, setAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [mode, setMode] = useState("CASH");
  const [message, setMessage] = useState("");
//console.log(" Deposit component loaded");

  const handleDeposit = async (e) => {
    e.preventDefault();
    console.log("Deposit button clicked");

    if (!account || !amount || amount <= 0) {
      alert("Please enter valid account and amount.");
      return;
    }

    try {
      const res = await fetch(
        "http://localhost:8080/bank-simulator/api/transactions/deposit",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            account: account.trim(),
            amount: parseFloat(amount),
            mode,
          }),
        }
      );

      console.log("Response status:", res.status);
      const text = await res.text();
      console.log("Response text:", text);

      if (res.ok) {
        setMessage(` ₹${amount} deposited successfully to ${account}`);
      } else {
        setMessage(`Deposit failed: ${text}`);
      }
    } catch (err) {
      console.error("Fetch failed:", err);
      setMessage("erver connection failed");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Deposit Money</h2>

      <form onSubmit={handleDeposit}>
        <input
          type="text"
          placeholder="Enter Account Number"
          value={account}
          onChange={(e) => setAccount(e.target.value)}
        />
        <br />
        <input
          type="number"
          placeholder="Enter Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />
        <br />
        <select value={mode} onChange={(e) => setMode(e.target.value)}>
          <option value="CASH">Cash</option>
          <option value="ONLINE">Online</option>
          <option value="CHEQUE">Cheque</option>
        </select>
        <br />
        <button type="submit">Deposit</button>
      </form>

      {message && <p>{message}</p>}
    </div>
  );
}
