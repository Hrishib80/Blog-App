import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './components/Navbar';
import { AuthProvider } from './context/AuthContext';
import CreatePost from './pages/CreatePost';
import Home from './pages/Home';
import Login from './pages/Login';
import PostDetail from './pages/PostDetail';
import Profile from './pages/Profile';
import Register from './pages/Register';
import Search from './pages/Search';
import ProtectedRoute from './ProtectedRoute';

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/search" element={<Search />} />
          <Route path="/post/:slug" element={<PostDetail />} />
          <Route element={<ProtectedRoute />}>
            <Route path="/create" element={<CreatePost />} />
            <Route path="/edit/:id" element={<CreatePost />} />
            <Route path="/profile/:username" element={<Profile />} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
