import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api';
import TiptapEditor from '../components/TiptapEditor';
import { useAuth } from '../context/AuthContext';

export default function CreatePost() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [form, setForm] = useState({ title: '', subtitle: '', content: '', tagNames: '', published: false });

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }
    if (id) {
      api.get(`/posts/${id}`).then(({ data }) => setForm((current) => ({ ...current, title: data.title, subtitle: data.subtitle, content: data.content, tagNames: data.tags?.join(', '), published: data.published })));
    }
  }, [id, user, navigate]);

  const submit = async (event) => {
    event.preventDefault();
    const payload = {
      ...form,
      tagNames: form.tagNames.split(',').map((item) => item.trim()).filter(Boolean),
    };
    if (id) {
      await api.put(`/posts/${id}`, payload);
    } else {
      await api.post('/posts', payload);
    }
    navigate('/');
  };

  return (
    <div className="page-shell">
      <form className="card form-card" onSubmit={submit}>
        <h2>{id ? 'Edit post' : 'Create post'}</h2>
        <input value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} placeholder="Title" />
        <input value={form.subtitle} onChange={(e) => setForm({ ...form, subtitle: e.target.value })} placeholder="Subtitle" />
        <TiptapEditor value={form.content} onChange={(content) => setForm({ ...form, content })} />
        <input value={form.tagNames} onChange={(e) => setForm({ ...form, tagNames: e.target.value })} placeholder="Tags (comma separated)" />
        <label className="checkbox-row">
          <input type="checkbox" checked={form.published} onChange={(e) => setForm({ ...form, published: e.target.checked })} />
          Publish now
        </label>
        <button className="primary-btn" type="submit">Save</button>
      </form>
    </div>
  );
}
