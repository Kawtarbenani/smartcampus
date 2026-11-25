import { useEffect, useState } from 'react';
import {
  Container, Typography, Box, TextField, Button, MenuItem,
  Table, TableBody, TableCell, TableHead, TableRow, Alert
} from '@mui/material';
import { etudiantService } from '../services/etudiantService';
import { formationService } from '../services/formationService';

const emptyForm = {
  nom: '', prenom: '', email: '', password: '',
  matricule: '', annee: 4, filiereId: ''
};

export default function Etudiants() {
  const [etudiants, setEtudiants] = useState([]);
  const [filieres, setFilieres] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [search, setSearch] = useState('');
  const [error, setError] = useState('');

  const loadData = async () => {
    try {
      const [etus, fils] = await Promise.all([
        etudiantService.getAll(),
        formationService.getFilieres()
      ]);
      setEtudiants(etus);
      setFilieres(fils);
    } catch {
      setError('Erreur lors du chargement des étudiants.');
    }
  };

  useEffect(() => { loadData(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    const payload = {
      ...form,
      annee: Number(form.annee),
      filiereId: form.filiereId ? Number(form.filiereId) : null
    };
    try {
      if (editingId) {
        await etudiantService.update(editingId, payload);
      } else {
        await etudiantService.create(payload);
      }
      setForm(emptyForm);
      setEditingId(null);
      loadData();
    } catch {
      setError("Erreur lors de l'enregistrement (vérifiez email/matricule unique).");
    }
  };

  const handleEdit = (etu) => {
    setEditingId(etu.id);
    setForm({
      nom: etu.nom,
      prenom: etu.prenom,
      email: etu.email,
      password: '',
      matricule: etu.matricule,
      annee: etu.annee,
      filiereId: etu.filiereId || ''
    });
  };

  const filtered = etudiants.filter(e =>
    `${e.nom} ${e.prenom} ${e.matricule}`.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        {editingId ? 'Modifier un Étudiant' : 'Gestion des Étudiants'}
      </Typography>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 3 }}>
        <TextField label="Matricule" required value={form.matricule} onChange={e => setForm({ ...form, matricule: e.target.value })} />
        <TextField label="Nom" required value={form.nom} onChange={e => setForm({ ...form, nom: e.target.value })} />
        <TextField label="Prénom" required value={form.prenom} onChange={e => setForm({ ...form, prenom: e.target.value })} />
        <TextField label="Email" type="email" required value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} />
        <TextField
          label={editingId ? 'Nouveau mot de passe (optionnel)' : 'Mot de passe'}
          type="password"
          required={!editingId}
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
        />
        <TextField label="Année" type="number" required value={form.annee} onChange={e => setForm({ ...form, annee: e.target.value })} />
        <TextField select label="Filière" sx={{ minWidth: 180 }} value={form.filiereId} onChange={e => setForm({ ...form, filiereId: e.target.value })}>
          <MenuItem value="">-- Aucune --</MenuItem>
          {filieres.map(f => <MenuItem key={f.id} value={f.id}>{f.code} - {f.nom}</MenuItem>)}
        </TextField>
        <Button type="submit" variant="contained">
          {editingId ? 'Enregistrer' : 'Ajouter'}
        </Button>
        {editingId && (
          <Button variant="outlined" onClick={() => { setEditingId(null); setForm(emptyForm); }}>
            Annuler
          </Button>
        )}
      </Box>

      <TextField
        label="Rechercher par nom ou matricule..."
        fullWidth
        sx={{ mb: 2 }}
        value={search}
        onChange={e => setSearch(e.target.value)}
      />

      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Matricule</TableCell><TableCell>Nom</TableCell><TableCell>Prénom</TableCell>
            <TableCell>Email</TableCell><TableCell>Année</TableCell><TableCell>Filière</TableCell><TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {filtered.map(e => (
            <TableRow key={e.id}>
              <TableCell>{e.matricule}</TableCell><TableCell>{e.nom}</TableCell><TableCell>{e.prenom}</TableCell>
              <TableCell>{e.email}</TableCell><TableCell>{e.annee}A</TableCell><TableCell>{e.filiereCode || '-'}</TableCell>
              <TableCell>
                <Button size="small" onClick={() => handleEdit(e)}>Modifier</Button>
                <Button size="small" color="error" onClick={() => etudiantService.remove(e.id).then(loadData)}>Supprimer</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Container>
  );
}