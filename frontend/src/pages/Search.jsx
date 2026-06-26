import { useState } from 'react';
import api from '../api';
import PostCard from '../components/PostCard';

export default function Search() {
  const [query, setQuery] = useState('');
  const [posts, setPosts] = useState([]);

  const submit = async (event) => {
    event.preventDefault();
    const { data } = await api.get(`/posts/search?query=${encodeURIComponent(query)}`);
    setPosts(data.content || []);
  };

  return (
    <div className="page-shell">
      <form className="card search-form" onSubmit={submit}>
        <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search posts" />
        <button className="primary-btn" type="submit">Search</button>
      </form>
      <div className="posts-grid">
        {posts.map((post) => (
          <PostCard key={post.id} post={post} onToggleLike={() => {}} />
        ))}
      </div>
    </div>
  );
}
