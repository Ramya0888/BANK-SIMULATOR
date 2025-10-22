import React, { useState } from "react";

export default function Transfer() {
  const [fromAcc, setFromAcc] = useState("");
  const [toAcc, setToAcc] = useState("");
  const [amount, setAmount] = useState("");
  const [mode, setMode] = useState("Online");
  const [pin, setPin] = useState("");

  const handleTransfer = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch("http://localhost:8080/bank-simulator/api/transactions/transfer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          from: fromAcc,
          to: toAcc,
          amount: parseFloat(amount),
          mode,
          pin: parseInt(pin)
        }),
      });

      if (!res.ok) {
        const errText = await res.text();
        throw new Error(errText || "Transfer failed");
      }

      const result = await res.json();
      alert(`Transfer successful! New Balance: ₹${result.balanceAmount || "Updated"}`);
      
      setFromAcc("");
      setToAcc("");
      setAmount("");
      setPin("");
    } catch (err) {
      alert("❌ " + err.message);
    }
  };

  return (
    <div className="form-container">
      <h2>Transfer Funds</h2>
      <form onSubmit={handleTransfer}>
        <input
          type="text"
          placeholder="From Account Number"
          value={fromAcc}
          onChange={(e) => setFromAcc(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="To Account Number"
          value={toAcc}
          onChange={(e) => setToAcc(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="PIN"
          value={pin}
          onChange={(e) => setPin(e.target.value)}
          required
        />
        <select value={mode} onChange={(e) => setMode(e.target.value)}>
          <option>Online</option>
          <option>NEFT</option>
          <option>IMPS</option>
          <option>Cash</option>
        </select>
        <button type="submit">Transfer</button>
      </form>
    </div>
  );
}
