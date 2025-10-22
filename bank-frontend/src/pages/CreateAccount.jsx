import React, { useState, useEffect } from "react";

export default function CreateAccount() {
  const username = localStorage.getItem("loggedInUser");
  const [customerId, setCustomerId] = useState("");
  const [formData, setFormData] = useState({
    accountType: "",
    bankName: "",
    branch: "",
    balance: "",
    status: "ACTIVE",
    accountNumber: "",
    ifscCode: "",
    nameOnAccount: "",
    phoneLinked: "",
    savingAmount: "",
  });

  const [message, setMessage] = useState("");

  // Fetch customerId using username
  useEffect(() => {
    fetch(
      `http://localhost:8080/bank-simulator/api/customers/by-username/${username}`
    )
      .then((res) => res.json())
      .then((data) => setCustomerId(data.customerId))
      .catch(() => setMessage(" Unable to fetch customer details"));
  }, [username]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const res = await fetch(
        "http://localhost:8080/bank-simulator/api/accounts",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ ...formData, customerId }),
        }
      );

      if (!res.ok) throw new Error("Account creation failed");

      const data = await res.json();
      setMessage(
        ` Account created successfully! Account Number: ${data.accountNumber}`
      );

      setFormData({
       accountType: "",
        bankName: "",
        branch: "",
        balance: "",
        status: "ACTIVE",
        accountNumber: "",
        ifscCode: "",
        nameOnAccount: "",
        phoneLinked: "",
        savingAmount: "",
      });
    } catch (err) {
      setMessage(` ${err.message}`);
    }
  };

  return (
    <div className="form-container">
      <h2>Create New Account</h2>
      <form onSubmit={handleSubmit}>
       
        <select
          name="accountType"
          value={formData.accountType}
          onChange={handleChange}
          required
        >
          <option value="">-- Select Type --</option>
          <option value="SAVINGS">SAVINGS</option>
          <option value="CURRENT">CURRENT</option>
        </select>
        <input
          name="bankName"
          placeholder="Bank Name"
          value={formData.bankName}
          onChange={handleChange}
          required
        />
        <input
          name="branch"
          placeholder="Branch"
          value={formData.branch}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="balance"
          placeholder="Balance"
          value={formData.balance}
          onChange={handleChange}
          required
        />
        <input
          name="accountNumber"
          placeholder="Account Number"
          value={formData.accountNumber}
          onChange={handleChange}
          required
        />
        <input
          name="ifscCode"
          placeholder="IFSC Code"
          value={formData.ifscCode}
          onChange={handleChange}
          required
        />
        <input
          name="nameOnAccount"
          placeholder="Name on Account"
          value={formData.nameOnAccount}
          onChange={handleChange}
          required
        />
        <input
          name="phoneLinked"
          placeholder="Linked Phone Number"
          value={formData.phoneLinked}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="savingAmount"
          placeholder="Saving Amount"
          value={formData.savingAmount}
          onChange={handleChange}
        />
        <button type="submit">Create Account</button>
      </form>

      {message && <p>{message}</p>}
    </div>
  );
}
