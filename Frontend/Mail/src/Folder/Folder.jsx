import React, { useState } from 'react';
import {
  Button,
  IconButton,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
} from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import MailIcon from '@mui/icons-material/Mail';
import SendIcon from '@mui/icons-material/Send';
import DraftsIcon from '@mui/icons-material/Drafts';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import axios from 'axios';

function Folder({
  setContent,
  name,
  setSelectedFolder,
  selectedFolder,
  folders,
  setMails,
  setFolders,
  setTriggerFetch
}) {

  const [isDialogOpen, setDialogOpen] = useState(false);
  const [newFolderName, setNewFolderName] = useState(name);

  function handleFolderSelected() {
    setSelectedFolder(name);
    setContent('mails');
    setTriggerFetch(true);
  }

  const folder = folders.find((folder) => folder.name === selectedFolder);
  
  async function handleDeleteFolder(folderId) {
    try {
      const response = await axios.delete(`http://localhost:8080/folder/${folderId}`);
      if (response.status === 200) {
        setFolders((prevFolders) => prevFolders.filter((f) => f.id !== folderId));
      }
    } catch (error) {
      console.error('Error deleting folder:', error);
    }
  }

  async function handleEditFolder(e) {
    if ( !newFolderName.trim()) return;

    const updatedFolder = { ...folder, name: newFolderName };

    try {
      const response = await axios.put(`http://localhost:8080/folder/edit/${folder.id}`, updatedFolder);
      if (response.status === 201) {
        setFolders((prevFolders) =>
          prevFolders.map((f) => (f.id === folder.id ? updatedFolder : f))
        );
        setDialogOpen(false); // Close dialog after success
      }
    } catch (error) {
      console.error('Error editing folder:', error.response?.data || error);
    }
  }

  return (
    <div style={{ display: 'flex', alignItems: 'center', width: '90%' }}>
      <Button
        style={{ flexGrow: 1, backgroundColor: '#aee7fe', color: 'black' }}
        variant="contained"
        endIcon={
          name === 'Sent' ? (
            <SendIcon />
          ) : name === 'Trash' ? (
            <AutoDeleteIcon />
          ) : name === 'Inbox' ? (
            <MailIcon />
          ) : name === 'Drafts' ? (
            <DraftsIcon />
          ) : (
            <ArrowForwardIosIcon />
          )
        }
        onClick={handleFolderSelected}
      >
        {name}
      </Button>
      {!(['Sent', 'Trash', 'Inbox', 'Drafts'].includes(name)) && folder && (
        <>
          <IconButton value={folder.id} onClick={() => handleDeleteFolder(folder.id)} aria-label="delete">
            <DeleteIcon />
          </IconButton>
          <IconButton value={folder.id} onClick={() => setDialogOpen(true)} aria-label="edit">
            <EditIcon />
          </IconButton>
        </>
      )}
      <Dialog open={isDialogOpen} onClose={() => setDialogOpen(false)}>
        <DialogTitle>Edit Folder</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Folder Name"
            type="text"
            fullWidth
            value={newFolderName}
            onChange={(e) => setNewFolderName(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDialogOpen(false)}>Cancel</Button>
          <Button onClick={handleEditFolder} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default Folder;
