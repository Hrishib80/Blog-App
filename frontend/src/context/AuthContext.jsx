import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import api from '../api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem('user');
    return savedUser ? JSON.parse(savedUser) : null;
  });
  const [token, setToken] = useState(() => localStorage.getItem('token') || '');

  useEffect(() => {
    if (token) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem('token');
    }
  }, [token]);

  useEffect(() => {
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    } else {
      localStorage.removeItem('user');
    }
  }, [user]);

  const login = async (credentials) => {
    const { data } = await api.post('/auth/login', credentials);
    setToken(data.token);
    setUser({
      id: data.id,
      username: data.username,
      displayName: data.displayName,
      avatarUrl: data.avatarUrl,
      role: data.role,
    });
    return data;
  };

  const register = async (payload) => {
    const { data } = await api.post('/auth/register', payload);
    setToken(data.token);
    setUser({
      id: data.id,
      username: data.username,
      displayName: data.displayName,
      avatarUrl: data.avatarUrl,
      role: data.role,
    });
    return data;
  };

  const logout = () => {
    setToken('');
    setUser(null);
  };

  const value = useMemo(() => ({ user, token, login, register, logout }), [user, token]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
