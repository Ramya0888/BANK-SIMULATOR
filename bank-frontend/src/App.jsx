import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Deposit from './pages/Deposit';
import Withdraw from './pages/Withdraw';
import Transfer from './pages/Transfer';
import CheckBalance from './pages/CheckBalance';
import UpdateProfile from './pages/UpdateProfile';
import CreateAccount from "./pages/CreateAccount";
import UpdateAccount from "./pages/UpdateAccount";
import TransactionHistory from "./pages/TransactionHistory";
import DeleteAccount from "./pages/DeleteAccount";
export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/withdraw" element={<Withdraw />} />
      <Route path="/transfer" element={<Transfer />} />
      <Route path="/deposit" element={<Deposit />} />
      <Route path="/check-balance" element={<CheckBalance />} />
      <Route path="/update-profile" element={<UpdateProfile />} />
            <Route path="/create-account" element={<CreateAccount />} />
      <Route path="/update-account" element={<UpdateAccount />} />
      <Route path="/delete-account" element={<DeleteAccount />} />
      <Route path="/transaction-history" element={<TransactionHistory />} />
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
}
