import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    password: "",
    email: "",
    phoneNumber: "",
    dob: "",
    aadharNumber: "",
    permanentAddress: "",
    state: "",
    country: "",
    city: "",
    age: "",
    gender: "",
    fatherName: "",
    motherName: "",
    pin: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/bank-simulator/api/customers", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      if (response.ok) {
        alert("Registration successful! You can now login.");
        navigate("/");
      } else {
        alert("Failed to register. Check details and try again.");
      }
    } catch (err) {
      alert("Server error. Please try again later.");
      console.error(err);
    }
  };

  return (
    <div className="login-container">
      <h2>Customer Registration</h2>
      <form onSubmit={handleRegister}>
        <input name="username" placeholder="Username" onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" onChange={handleChange} required />
        <input name="email" placeholder="Email" onChange={handleChange} />
        <input name="phoneNumber" placeholder="Phone Number" onChange={handleChange} />
        <input name="dob" type="date" placeholder="Date of Birth" onChange={handleChange} />
        <input name="aadharNumber" placeholder="Aadhar Number" onChange={handleChange} />
        <input name="permanentAddress" placeholder="Address" onChange={handleChange} />
        <input name="state" placeholder="State" onChange={handleChange} />
        <input name="country" placeholder="Country" onChange={handleChange} />
        <input name="city" placeholder="City" onChange={handleChange} />
        <input name="age" placeholder="Age" type="number" onChange={handleChange} />
        <input name="gender" placeholder="Gender" onChange={handleChange} />
        <input name="fatherName" placeholder="Father Name" onChange={handleChange} />
        <input name="motherName" placeholder="Mother Name" onChange={handleChange} />
        <input name="pin" placeholder="PIN" type="number" onChange={handleChange} />
        <button type="submit" >Register</button>
      </form>
      <p>Already have an account? <Link to="/">Login</Link></p>
    </div>
  );
}
