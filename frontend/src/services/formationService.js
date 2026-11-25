import axios from 'axios';

const API_URL = 'http://localhost:8081/api/admin';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return { headers: { Authorization: `Bearer ${token}` } };
};

export const formationService = {
  getFilieres: () => axios.get(`${API_URL}/filieres`, getAuthHeaders()).then(r => r.data),
  createFiliere: (data) => axios.post(`${API_URL}/filieres`, data, getAuthHeaders()).then(r => r.data),
  deleteFiliere: (id) => axios.delete(`${API_URL}/filieres/${id}`, getAuthHeaders()),

  getSemestres: () => axios.get(`${API_URL}/semestres`, getAuthHeaders()).then(r => r.data),
  createSemestre: (data) => axios.post(`${API_URL}/semestres`, data, getAuthHeaders()).then(r => r.data),
  deleteSemestre: (id) => axios.delete(`${API_URL}/semestres/${id}`, getAuthHeaders()),

  getModules: () => axios.get(`${API_URL}/modules`, getAuthHeaders()).then(r => r.data),
  createModule: (data) => axios.post(`${API_URL}/modules`, data, getAuthHeaders()).then(r => r.data),
  deleteModule: (id) => axios.delete(`${API_URL}/modules/${id}`, getAuthHeaders()),
};