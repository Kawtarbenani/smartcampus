import axios from 'axios';

const API_URL = 'http://localhost:8081/api/admin';

const getAuthHeaders = () => {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const token = user.token || localStorage.getItem('token');
  return { headers: { Authorization: `Bearer ${token}` } };
};

export const enseignantService = {
  getAll: () => axios.get(`${API_URL}/enseignants`, getAuthHeaders()).then(r => r.data),
  create: (data) => axios.post(`${API_URL}/enseignants`, data, getAuthHeaders()).then(r => r.data),
  remove: (id) => axios.delete(`${API_URL}/enseignants/${id}`, getAuthHeaders()),

  getAffectations: () => axios.get(`${API_URL}/affectations`, getAuthHeaders()).then(r => r.data),
  affecterModule: (data) => axios.post(`${API_URL}/affectations`, data, getAuthHeaders()).then(r => r.data),
  removeAffectation: (id) => axios.delete(`${API_URL}/affectations/${id}`, getAuthHeaders()),
};