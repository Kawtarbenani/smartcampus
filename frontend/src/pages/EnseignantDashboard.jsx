import { useEffect, useState } from 'react';
import {
  Container, Typography, Grid, Paper, Table, TableBody,
  TableCell, TableHead, TableRow, Alert, Chip
} from '@mui/material';
import { enseignantService } from '../services/enseignantService';

export default function EnseignantDashboard() {
  const [modules, setModules] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    enseignantService.getMesModules()
      .then(setModules)
      .catch(() => setError('Impossible de charger vos modules assignés (connectez-vous en tant que Enseignant).'));
  }, []);

  const totalHeures = modules.reduce((sum, m) => sum + (m.volumeHoraire || 0), 0);
  const totalCredits = modules.reduce((sum, m) => sum + (m.credits || 0), 0);

  return (
    <Container sx={{ mt: 4, mb: 4, bgcolor: '#ffffff', color: '#000000', p: 4, borderRadius: 2, boxShadow: 3 }}>
      <Typography variant="h4" gutterBottom>
        Dashboard Enseignant — Mes Modules Assignés
      </Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Grid container spacing={2} sx={{ mb: 3 }}>
        <Grid item xs={12} sm={4}>
          <Paper sx={{ p: 2, bgcolor: '#e3f2fd' }}>
            <Typography variant="subtitle2">Modules assignés</Typography>
            <Typography variant="h4">{modules.length}</Typography>
          </Paper>
        </Grid>
        <Grid item xs={12} sm={4}>
          <Paper sx={{ p: 2, bgcolor: '#e8f5e9' }}>
            <Typography variant="subtitle2">Volume horaire total</Typography>
            <Typography variant="h4">{totalHeures} h</Typography>
          </Paper>
        </Grid>
        <Grid item xs={12} sm={4}>
          <Paper sx={{ p: 2, bgcolor: '#fff3e0' }}>
            <Typography variant="subtitle2">Total crédits</Typography>
            <Typography variant="h4">{totalCredits}</Typography>
          </Paper>
        </Grid>
      </Grid>

      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Code</TableCell>
            <TableCell>Module</TableCell>
            <TableCell>Filière</TableCell>
            <TableCell>Semestre</TableCell>
            <TableCell>Crédits</TableCell>
            <TableCell>Volume Horaire</TableCell>
            <TableCell>Année</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {modules.length === 0 ? (
            <TableRow>
              <TableCell colSpan={7} align="center">Aucun module assigné pour le moment.</TableCell>
            </TableRow>
          ) : (
            modules.map(m => (
              <TableRow key={`${m.moduleId}-${m.anneeUniversitaire}`}>
                <TableCell><Chip label={m.code} color="primary" size="small" /></TableCell>
                <TableCell>{m.nom}</TableCell>
                <TableCell>{m.filiereCode} - {m.filiereNom}</TableCell>
                <TableCell>{m.semestreCode}</TableCell>
                <TableCell>{m.credits}</TableCell>
                <TableCell>{m.volumeHoraire} h</TableCell>
                <TableCell>{m.anneeUniversitaire}</TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </Container>
  );
}