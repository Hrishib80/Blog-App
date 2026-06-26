import { Link } from 'react-router-dom';
import { formatDistanceToNow } from 'date-fns';

export default function PostCard({ post, onToggleLike }) {
  return (
    <article className="card post-card">
      <div className="card-header">
        <div>
          <p className="eyebrow">{post.tags?.join(' • ') || 'STORY'}</p>
          <h3>{post.title}</h3>
        </div>
        <span className="pill">{post.readTime} min read</span>
      </div>
      <p className="muted">{post.subtitle || post.content?.slice(0, 140)}…</p>
      <div className="meta-row">
        <span>By {post.authorName || 'Anonymous'}</span>
        <span>{formatDistanceToNow(new Date(post.createdAt), { addSuffix: true })}</span>
      </div>
      <div className="actions-row">
        <Link className="primary-btn" to={`/post/${post.slug}`}>Read more</Link>
        <button className="ghost-btn" onClick={() => onToggleLike(post.id)}>
          ♥ {post.likeCount}
        </button>
      </div>
    </article>
  );
}
