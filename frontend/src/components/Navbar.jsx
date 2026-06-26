import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <nav className="navbar">
      <Link to="/" className="brand">Mind-A-Thought</Link>
      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/search">Search</Link>
        {user ? (
          <>
            <Link to={`/profile/${user.username}`}>Profile</Link>
            <Link to="/create">Write</Link>
            <button className="ghost-btn" onClick={() => { logout(); navigate('/login'); }}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
}
