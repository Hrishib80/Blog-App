import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const { login } = useAuth();
  const [form, setForm] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const submit = async (event) => {
    event.preventDefault();
    await login(form);
    navigate('/');
  };

  return (
    <div className="auth-shell">
      <form className="card auth-card" onSubmit={submit}>
        <h2>Welcome back</h2>
        <input value={form.username} onChange={(e) => setForm({ ...form, username: e.target.value })} placeholder="Username" />
        <input type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} placeholder="Password" />
        <button className="primary-btn" type="submit">Login</button>
        <p className="muted">No account yet? <Link to="/register">Create one</Link></p>
      </form>
    </div>
  );
}
