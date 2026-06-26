import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api';
import { useAuth } from '../context/AuthContext';

export default function PostDetail() {
  const { slug } = useParams();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState('');
  const { user } = useAuth();
  const navigate = useNavigate();

  const loadPost = async () => {
    const { data } = await api.get(`/posts/${slug}`);
    setPost(data);
    const commentsRes = await api.get(`/posts/${data.id}/comments`);
    setComments(commentsRes.data);
  };

  useEffect(() => {
    loadPost();
  }, [slug]);

  const submitComment = async (event) => {
    event.preventDefault();
    if (!user) {
      navigate('/login');
      return;
    }
    await api.post(`/posts/${post.id}/comments`, { content: commentText });
    setCommentText('');
    loadPost();
  };

  if (!post) return <div className="page-shell">Loading…</div>;

  return (
    <div className="page-shell detail-page">
      <article className="card">
        <h1>{post.title}</h1>
        <p className="muted">{post.subtitle}</p>
        <div className="meta-row">
          <span>By {post.authorName}</span>
          <span>{post.tags?.join(' • ')}</span>
        </div>
        <div className="content-body" dangerouslySetInnerHTML={{ __html: post.content }} />
      </article>
      <section className="card">
        <h3>Comments</h3>
        <form onSubmit={submitComment} className="comment-form">
          <textarea value={commentText} onChange={(e) => setCommentText(e.target.value)} placeholder="Share your thoughts" />
          <button className="primary-btn" type="submit">Comment</button>
        </form>
        <div className="comment-list">
          {comments.map((comment) => (
            <div key={comment.id} className="comment-item">
              <strong>{comment.authorName || 'Reader'}</strong>
              <p>{comment.content}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
