import React, { useState } from "react";

export default function TransactionHistory() {
  const [account, setAccount] = useState("");

  const handleDownload = async () => {
    if (!account) {
      alert("Please enter account number");
      return;
    }

    try {
      const res = await fetch(
        `http://localhost:8080/bank-simulator/api/transactions/${account}/download`
      );

      if (!res.ok) {
        throw new Error("Failed to download transaction file");
      }

      const blob = await res.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `transactions_${account}.xlsx`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
      alert("Transactions downloaded successfully!");
    } catch (error) {
      alert("Error: " + error.message);
    }
  };

  return (
    <div className="form-container">
      <h2>Download Transaction History</h2>
      <input
        type="text"
        placeholder="Enter Account Number"
        value={account}
        onChange={(e) => setAccount(e.target.value)}
      />
      <button onClick={handleDownload}>Download Excel</button>
    </div>
  );
}
