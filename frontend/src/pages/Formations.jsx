import { useEffect, useState } from 'react';
import {
  Container, Typography, Tabs, Tab, Box, TextField, Button,
  Table, TableBody, TableCell, TableHead, TableRow, MenuItem, Alert
} from '@mui/material';
import { formationService } from '../services/formationService';

export default function Formations() {
  const [tab, setTab] = useState(0);
  const [filieres, setFilieres] = useState([]);
  const [semestres, setSemestres] = useState([]);
  const [modules, setModules] = useState([]);
  const [error, setError] = useState('');

  const [filiereForm, setFiliereForm] = useState({ code: '', nom: '', description: '' });
  const [semestreForm, setSemestreForm] = useState({ code: '', libelle: '', anneeUniversitaire: '2026-2027' });
  const [moduleForm, setModuleForm] = useState({ code: '', nom: '', credits: 3, volumeHoraire: 36, filiereId: '', semestreId: '' });

  const loadAll = async () => {
    try {
      const [f, s, m] = await Promise.all([
        formationService.getFilieres(),
        formationService.getSemestres(),
        formationService.getModules(),
      ]);
      setFilieres(f);
      setSemestres(s);
      setModules(m);
    } catch {
      setError("Erreur lors du chargement des formations (vérifiez votre connexion Admin).");
    }
  };

  useEffect(() => { loadAll(); }, []);

  const handleAddFiliere = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await formationService.createFiliere(filiereForm);
      setFiliereForm({ code: '', nom: '', description: '' });
      loadAll();
    } catch { setError("Impossible d'ajouter la filière."); }
  };

  const handleAddSemestre = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await formationService.createSemestre(semestreForm);
      setSemestreForm({ code: '', libelle: '', anneeUniversitaire: '2026-2027' });
      loadAll();
    } catch { setError("Impossible d'ajouter le semestre."); }
  };

  const handleAddModule = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await formationService.createModule({
        ...moduleForm,
        credits: Number(moduleForm.credits),
        volumeHoraire: Number(moduleForm.volumeHoraire),
        filiereId: Number(moduleForm.filiereId),
        semestreId: Number(moduleForm.semestreId),
      });
      setModuleForm({ code: '', nom: '', credits: 3, volumeHoraire: 36, filiereId: '', semestreId: '' });
      loadAll();
    } catch { setError("Impossible d'ajouter le module."); }
  };

  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>Gestion des Formations</Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Tabs value={tab} onChange={(_, v) => setTab(v)} sx={{ mb: 3 }}>
        <Tab label="Filières" />
        <Tab label="Semestres" />
        <Tab label="Modules" />
      </Tabs>

      {tab === 0 && (
        <Box>
          <Box component="form" onSubmit={handleAddFiliere} sx={{ display: 'flex', gap: 2, mb: 3 }}>
            <TextField label="Code" required value={filiereForm.code} onChange={e => setFiliereForm({ ...filiereForm, code: e.target.value })} />
            <TextField label="Nom" required value={filiereForm.nom} onChange={e => setFiliereForm({ ...filiereForm, nom: e.target.value })} />
            <TextField label="Description" value={filiereForm.description} onChange={e => setFiliereForm({ ...filiereForm, description: e.target.value })} />
            <Button type="submit" variant="contained">Ajouter</Button>
          </Box>
          <Table>
            <TableHead><TableRow><TableCell>Code</TableCell><TableCell>Nom</TableCell><TableCell>Description</TableCell><TableCell>Action</TableCell></TableRow></TableHead>
            <TableBody>
              {filieres.map(f => (
                <TableRow key={f.id}>
                  <TableCell>{f.code}</TableCell><TableCell>{f.nom}</TableCell><TableCell>{f.description}</TableCell>
                  <TableCell><Button color="error" onClick={() => formationService.deleteFiliere(f.id).then(loadAll)}>Supprimer</Button></TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
      )}

      {tab === 1 && (
        <Box>
          <Box component="form" onSubmit={handleAddSemestre} sx={{ display: 'flex', gap: 2, mb: 3 }}>
            <TextField label="Code (ex: S7)" required value={semestreForm.code} onChange={e => setSemestreForm({ ...semestreForm, code: e.target.value })} />
            <TextField label="Libellé" required value={semestreForm.libelle} onChange={e => setSemestreForm({ ...semestreForm, libelle: e.target.value })} />
            <TextField label="Année Univ." required value={semestreForm.anneeUniversitaire} onChange={e => setSemestreForm({ ...semestreForm, anneeUniversitaire: e.target.value })} />
            <Button type="submit" variant="contained">Ajouter</Button>
          </Box>
          <Table>
            <TableHead><TableRow><TableCell>Code</TableCell><TableCell>Libellé</TableCell><TableCell>Année</TableCell><TableCell>Action</TableCell></TableRow></TableHead>
            <TableBody>
              {semestres.map(s => (
                <TableRow key={s.id}>
                  <TableCell>{s.code}</TableCell><TableCell>{s.libelle}</TableCell><TableCell>{s.anneeUniversitaire}</TableCell>
                  <TableCell><Button color="error" onClick={() => formationService.deleteSemestre(s.id).then(loadAll)}>Supprimer</Button></TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
      )}

      {tab === 2 && (
        <Box>
          <Box component="form" onSubmit={handleAddModule} sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 3 }}>
            <TextField label="Code" required value={moduleForm.code} onChange={e => setModuleForm({ ...moduleForm, code: e.target.value })} />
            <TextField label="Nom" required value={moduleForm.nom} onChange={e => setModuleForm({ ...moduleForm, nom: e.target.value })} />
            <TextField label="Crédits" type="number" required value={moduleForm.credits} onChange={e => setModuleForm({ ...moduleForm, credits: e.target.value })} />
            <TextField label="Volume Horaire" type="number" required value={moduleForm.volumeHoraire} onChange={e => setModuleForm({ ...moduleForm, volumeHoraire: e.target.value })} />
            <TextField select label="Filière" required sx={{ minWidth: 160 }} value={moduleForm.filiereId} onChange={e => setModuleForm({ ...moduleForm, filiereId: e.target.value })}>
              {filieres.map(f => <MenuItem key={f.id} value={f.id}>{f.code} - {f.nom}</MenuItem>)}
            </TextField>
            <TextField select label="Semestre" required sx={{ minWidth: 140 }} value={moduleForm.semestreId} onChange={e => setModuleForm({ ...moduleForm, semestreId: e.target.value })}>
              {semestres.map(s => <MenuItem key={s.id} value={s.id}>{s.code}</MenuItem>)}
            </TextField>
            <Button type="submit" variant="contained">Ajouter</Button>
          </Box>
          <Table>
            <TableHead><TableRow><TableCell>Code</TableCell><TableCell>Nom</TableCell><TableCell>Crédits</TableCell><TableCell>Heures</TableCell><TableCell>Filière</TableCell><TableCell>Semestre</TableCell><TableCell>Action</TableCell></TableRow></TableHead>
            <TableBody>
              {modules.map(m => (
                <TableRow key={m.id}>
                  <TableCell>{m.code}</TableCell><TableCell>{m.nom}</TableCell><TableCell>{m.credits}</TableCell>
                  <TableCell>{m.volumeHoraire}h</TableCell><TableCell>{m.filiere?.code}</TableCell><TableCell>{m.semestre?.code}</TableCell>
                  <TableCell><Button color="error" onClick={() => formationService.deleteModule(m.id).then(loadAll)}>Supprimer</Button></TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
      )}
    </Container>
  );
}