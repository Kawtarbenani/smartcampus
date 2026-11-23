import axios from 'axios';

// URL de base de votre backend Spring Boot
const API_URL = 'http://localhost:8081/api/auth';

export const authService = {
  // Fonction de connexion
  login: async (email, password) => {
    // 1. Envoi de la requête POST au backend
    const response = await axios.post(`${API_URL}/login`, {
      email,
      password,
    });

    // 2. Si le token est retourné, on le stocke dans le LocalStorage du navigateur
    if (response.data && response.data.token) {
      localStorage.setItem('user', JSON.stringify(response.data));
    }

    return response.data;
  },

  // Fonction de déconnexion
  logout: () => {
    localStorage.removeItem('user');
  },

  // Récupérer l'utilisateur actuellement connecté
  getCurrentUser: () => {
    return JSON.parse(localStorage.getItem('user'));
  }
};