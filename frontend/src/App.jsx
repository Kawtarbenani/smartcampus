import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Formations from './pages/Formations';
import Etudiants from './pages/Etudiants';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        {/* Redirection automatique de la racine / vers /login */}
        <Route path="/admin/formations" element={<Formations />} />
        <Route path="/admin/etudiants" element={<Etudiants />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;