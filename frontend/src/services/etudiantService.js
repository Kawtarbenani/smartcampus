import axios from 'axios';

const API_URL = 'http://localhost:8081/api/admin/etudiants';

const getAuthHeaders = () => {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const token = user.token || localStorage.getItem('token');
  return { headers: { Authorization: `Bearer ${token}` } };
};

export const etudiantService = {
  getAll: (search = '', filiereId = '') =>
    axios.get(API_URL, {
      ...getAuthHeaders(),
      params: {
        search: search || undefined,
        filiereId: filiereId || undefined,
      },
    }).then(r => r.data),  create: (data) => axios.post(API_URL, data, getAuthHeaders()).then(r => r.data),
  update: (id, data) => axios.put(`${API_URL}/${id}`, data, getAuthHeaders()).then(r => r.data),
  remove: (id) => axios.delete(`${API_URL}/${id}`, getAuthHeaders()),
};