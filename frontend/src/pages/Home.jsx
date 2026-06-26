import { useEffect, useState } from 'react';
import api from '../api';
import { useAuth } from '../context/AuthContext';
import PostCard from '../components/PostCard';

export default function Home() {
  const { user } = useAuth();
  const [posts, setPosts] = useState([]);

  const loadPosts = async () => {
    const { data } = await api.get('/posts');
    setPosts(data.content || []);
  };

  useEffect(() => {
    loadPosts();
  }, []);

  const toggleLike = async (postId) => {
    if (!user) return;
    const { data } = await api.post(`/posts/${postId}/like`);
    setPosts((current) => current.map((post) => (post.id === data.id ? data : post)));
  };

  return (
    <div className="page-shell">
      <section className="hero-panel">
        <div className="hero-copy">
          <p className="eyebrow hero-eyebrow">MIND-A-THOUGHT</p>
          <h1>Turn every idea into a vivid, vibrant story.</h1>
          <p className="muted hero-text">A sleek, playful writing space with white canvas energy and bold yellow motion that brings your thoughts to life.</p>
        </div>
      </section>
      <div className="posts-grid">
        {posts.map((post) => (
          <PostCard key={post.id} post={post} onToggleLike={toggleLike} />
        ))}
      </div>
    </div>
  );
}
