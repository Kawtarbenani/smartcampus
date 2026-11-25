import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Formations from './pages/Formations';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        {/* Redirection automatique de la racine / vers /login */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
      <Route path="/admin/formations" element={<Formations />} />
    </Router>
  );
}

export default App;