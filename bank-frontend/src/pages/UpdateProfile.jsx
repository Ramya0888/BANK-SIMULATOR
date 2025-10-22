import React, { useEffect, useState } from "react";

export default function UpdateProfile() {
  const username = localStorage.getItem("loggedInUser");
  const [customer, setCustomer] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/bank-simulator/api/customers/by-username/${username}`
        );
        if (!res.ok) throw new Error("User not found");
        const data = await res.json();

       
        if (data.dob) {
          const date = new Date(data.dob);
          if (!isNaN(date.getTime())) {
            const yyyy = date.getFullYear();
            const mm = String(date.getMonth() + 1).padStart(2, "0");
            const dd = String(date.getDate()).padStart(2, "0");
            data.dob = `${yyyy}-${mm}-${dd}`;
          }
        }

        setCustomer(data);
      } catch (err) {
        alert(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchCustomer();
  }, [username]);

  const handleChange = (e) => {
    setCustomer({ ...customer, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const updatedCustomer = { ...customer };
      delete updatedCustomer.customerId;

      const res = await fetch(
        `http://localhost:8080/bank-simulator/api/customers/${customer.customerId}`,
        {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(updatedCustomer),
        }
      );

      if (!res.ok) throw new Error("Update failed");
      alert("Profile updated successfully!");
    } catch (err) {
      alert(err.message);
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div className="form-container">
      <h2>Update Profile</h2>
      {customer && (
        <form onSubmit={handleUpdate}>
       
          <input type="text" name="username" value={customer.username || ""} disabled />
          <input type="text" name="customerId" value={customer.customerId || ""} disabled />

       
          <input
            type="password"
            name="password"
            value={customer.password || ""}
            onChange={handleChange}
            placeholder="Password"
          />
          <input
            type="email"
            name="email"
            value={customer.email || ""}
            onChange={handleChange}
            placeholder="Email"
          />
          <input
            type="text"
            name="phoneNumber"
            value={customer.phoneNumber || ""}
            onChange={handleChange}
            placeholder="Phone Number"
          />
          <input
            type="date"
            name="dob"
            value={customer.dob || ""}
            onChange={handleChange}
          />
          <input
            type="text"
            name="aadharNumber"
            value={customer.aadharNumber || ""}
            onChange={handleChange}
            placeholder="Aadhar Number"
          />
          <input
            type="text"
            name="permanentAddress"
            value={customer.permanentAddress || ""}
            onChange={handleChange}
            placeholder="Address"
          />
          <input
            type="text"
            name="city"
            value={customer.city || ""}
            onChange={handleChange}
            placeholder="City"
          />
          <input
            type="text"
            name="state"
            value={customer.state || ""}
            onChange={handleChange}
            placeholder="State"
          />
          <input
            type="text"
            name="country"
            value={customer.country || ""}
            onChange={handleChange}
            placeholder="Country"
          />
          <input
            type="number"
            name="age"
            value={customer.age || ""}
            onChange={handleChange}
            placeholder="Age"
          />
          <input
            type="text"
            name="gender"
            value={customer.gender || ""}
            onChange={handleChange}
            placeholder="Gender"
          />
          <input
            type="text"
            name="fatherName"
            value={customer.fatherName || ""}
            onChange={handleChange}
            placeholder="Father Name"
          />
          <input
            type="text"
            name="motherName"
            value={customer.motherName || ""}
            onChange={handleChange}
            placeholder="Mother Name"
          />
          <input
            type="number"
            name="pin"
            value={customer.pin || ""}
            onChange={handleChange}
            placeholder="PIN"
          />
          <button type="submit">Update</button>
        </form>
      )}
    </div>
  );
}
