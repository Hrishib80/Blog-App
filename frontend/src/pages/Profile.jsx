import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api';
import { useAuth } from '../context/AuthContext';

export default function Profile() {
  const { username } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [profile, setProfile] = useState(null);

  const loadProfile = async () => {
    const { data } = await api.get(`/users/${username}`);
    setProfile(data);
  };

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }
    loadProfile();
  }, [username, user, navigate]);

  if (!profile) return <div className="page-shell">Loading…</div>;

  return (
    <div className="page-shell">
      <div className="card profile-card">
        <h2>{profile.displayName}</h2>
        <p className="muted">@{profile.username}</p>
        <p>{profile.bio}</p>
        <div className="meta-row">
          <span>{profile.postCount} posts</span>
          <span>{profile.followerCount} followers</span>
          <span>{profile.followingCount} following</span>
        </div>
        <button className="primary-btn" onClick={() => api.post(`/users/${profile.username}/follow`) && loadProfile()}>Follow</button>
      </div>
    </div>
  );
}
