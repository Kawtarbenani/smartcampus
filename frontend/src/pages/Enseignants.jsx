import { useEffect, useState } from 'react';
import {
  Container, Typography, Tabs, Tab, Box, TextField, Button,
  MenuItem, Table, TableBody, TableCell, TableHead, TableRow, Alert
} from '@mui/material';
import { enseignantService } from '../services/enseignantService';
import { formationService } from '../services/formationService';

export default function Enseignants() {
  const [tab, setTab] = useState(0);
  const [enseignants, setEnseignants] = useState([]);
  const [modules, setModules] = useState([]);
  const [affectations, setAffectations] = useState([]);
  const [error, setError] = useState('');

  const [profForm, setProfForm] = useState({
    nom: '', prenom: '', email: '', password: '', specialite: '', grade: ''
  });

  const [affForm, setAffForm] = useState({
    enseignantId: '', moduleId: '', anneeUniversitaire: '2026-2027'
  });

  const loadAll = async () => {
    try {
      const [profs, mods, affs] = await Promise.all([
        enseignantService.getAll(),
        formationService.getModules(),
        enseignantService.getAffectations(),
      ]);
      setEnseignants(profs);
      setModules(mods);
      setAffectations(affs);
    } catch {
      setError('Erreur lors du chargement des enseignants et affectations.');
    }
  };

  useEffect(() => { loadAll(); }, []);

  const handleAddProf = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await enseignantService.create(profForm);
      setProfForm({ nom: '', prenom: '', email: '', password: '', specialite: '', grade: '' });
      loadAll();
    } catch {
      setError("Impossible de créer l'enseignant (email déjà utilisé ?).");
    }
  };

  const handleAffecter = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await enseignantService.affecterModule({
        ...affForm,
        enseignantId: Number(affForm.enseignantId),
        moduleId: Number(affForm.moduleId),
      });
      setAffForm({ enseignantId: '', moduleId: '', anneeUniversitaire: '2026-2027' });
      loadAll();
    } catch {
      setError("Impossible d'affecter ce module (déjà affecté ?).");
    }
  };

  return (
    <Container sx={{ mt: 4, mb: 4, bgcolor: '#ffffff', color: '#000000', p: 4, borderRadius: 2, boxShadow: 3 }}>
      <Typography variant="h4" gutterBottom>Gestion des Enseignants & Affectations</Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Tabs value={tab} onChange={(_, v) => setTab(v)} sx={{ mb: 3 }}>
        <Tab label="Enseignants" />
        <Tab label="Affectation des Modules" />
      </Tabs>

      {tab === 0 && (
        <Box>
          <Box component="form" onSubmit={handleAddProf} sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 3 }}>
            <TextField label="Nom" required value={profForm.nom} onChange={e => setProfForm({ ...profForm, nom: e.target.value })} />
            <TextField label="Prénom" required value={profForm.prenom} onChange={e => setProfForm({ ...profForm, prenom: e.target.value })} />
            <TextField label="Email" type="email" required value={profForm.email} onChange={e => setProfForm({ ...profForm, email: e.target.value })} />
            <TextField label="Mot de passe" type="password" required value={profForm.password} onChange={e => setProfForm({ ...profForm, password: e.target.value })} />
            <TextField label="Spécialité" required value={profForm.specialite} onChange={e => setProfForm({ ...profForm, specialite: e.target.value })} />
            <TextField label="Grade" value={profForm.grade} onChange={e => setProfForm({ ...profForm, grade: e.target.value })} />
            <Button type="submit" variant="contained">Créer Enseignant</Button>
          </Box>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nom</TableCell><TableCell>Prénom</TableCell><TableCell>Email</TableCell>
                <TableCell>Spécialité</TableCell><TableCell>Grade</TableCell><TableCell>Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {enseignants.map(p => (
                <TableRow key={p.id}>
                  <TableCell>{p.nom}</TableCell><TableCell>{p.prenom}</TableCell><TableCell>{p.email}</TableCell>
                  <TableCell>{p.specialite}</TableCell><TableCell>{p.grade || '-'}</TableCell>
                  <TableCell>
                    <Button color="error" onClick={() => enseignantService.remove(p.id).then(loadAll)}>Supprimer</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
      )}

      {tab === 1 && (
        <Box>
          <Box component="form" onSubmit={handleAffecter} sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 3 }}>
            <TextField select label="Enseignant" required sx={{ minWidth: 220 }} value={affForm.enseignantId} onChange={e => setAffForm({ ...affForm, enseignantId: e.target.value })}>
              {enseignants.map(p => <MenuItem key={p.id} value={p.id}>{p.prenom} {p.nom} ({p.specialite})</MenuItem>)}
            </TextField>
            <TextField select label="Module" required sx={{ minWidth: 220 }} value={affForm.moduleId} onChange={e => setAffForm({ ...affForm, moduleId: e.target.value })}>
              {modules.map(m => <MenuItem key={m.id} value={m.id}>{m.code} - {m.nom}</MenuItem>)}
            </TextField>
            <TextField label="Année Univ." required value={affForm.anneeUniversitaire} onChange={e => setAffForm({ ...affForm, anneeUniversitaire: e.target.value })} />
            <Button type="submit" variant="contained">Affecter Module</Button>
          </Box>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Enseignant</TableCell><TableCell>Code Module</TableCell><TableCell>Module</TableCell>
                <TableCell>Année</TableCell><TableCell>Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {affectations.map(a => (
                <TableRow key={a.id}>
                  <TableCell>{a.enseignantNomComplet}</TableCell><TableCell>{a.moduleCode}</TableCell>
                  <TableCell>{a.moduleNom}</TableCell><TableCell>{a.anneeUniversitaire}</TableCell>
                  <TableCell>
                    <Button color="error" onClick={() => enseignantService.removeAffectation(a.id).then(loadAll)}>Retirer</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
      )}
    </Container>
  );
}