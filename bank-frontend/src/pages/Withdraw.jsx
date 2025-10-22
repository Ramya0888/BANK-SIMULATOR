import React, { useState } from "react";

export default function Withdraw() {
  const [account, setAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [pin, setPin] = useState("");
  const [message, setMessage] = useState("");

  const handleWithdraw = async (e) => {
    e.preventDefault();

    if (!account || !amount || !pin) {
      alert("Please fill all fields!");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/bank-simulator/api/transactions/withdraw", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          account,
          amount: parseFloat(amount),
          pin: parseInt(pin),
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setMessage(`Withdrawal successful!`);
      } else {
        const errorText = await response.text();
        setMessage(`Error: ${errorText}`);
      }
    } catch (err) {
      setMessage("Server error while processing withdrawal");
    }
  };

  return (
    <div className="transaction-container">
      <h2>Withdraw Amount</h2>
      <form onSubmit={handleWithdraw}>
        <input
          type="text"
          placeholder="Account Number"
          value={account}
          onChange={(e) => setAccount(e.target.value)}
        />
        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />
        <input
          type="password"
          placeholder="Enter PIN"
          value={pin}
          onChange={(e) => setPin(e.target.value)}
        />
        <button type="submit">Withdraw</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}
