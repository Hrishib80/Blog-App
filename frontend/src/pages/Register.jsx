import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Register() {
  const { register } = useAuth();
  const [form, setForm] = useState({ username: '', email: '', password: '', displayName: '' });
  const navigate = useNavigate();

  const submit = async (event) => {
    event.preventDefault();
    await register(form);
    navigate('/');
  };

  return (
    <div className="auth-shell">
      <form className="card auth-card" onSubmit={submit}>
        <h2>Create your account</h2>
        <input value={form.displayName} onChange={(e) => setForm({ ...form, displayName: e.target.value })} placeholder="Display name" />
        <input value={form.username} onChange={(e) => setForm({ ...form, username: e.target.value })} placeholder="Username" />
        <input value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} placeholder="Email" />
        <input type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} placeholder="Password" />
        <button className="primary-btn" type="submit">Register</button>
        <p className="muted">Already have an account? <Link to="/login">Login</Link></p>
      </form>
    </div>
  );
}
