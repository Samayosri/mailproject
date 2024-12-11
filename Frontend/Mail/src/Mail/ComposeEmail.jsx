import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  TextField,
  DialogActions,
  Button,
} from '@mui/material';

const ComposeEmail = ({ open, onClose }) => {
  const [email, setEmail] = useState({ subject: '', body: '' });

  const handleChange = (e) => {
    setEmail({ ...email, [e.target.name]: e.target.value });
  };

  const handleSend = () => {
    console.log('Email sent:', email);
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Compose Email</DialogTitle>
      <DialogContent>
        <TextField
          fullWidth
          margin="normal"
          label="Subject"
          name="subject"
          value={email.subject}
          onChange={handleChange}
        />
        <TextField
          fullWidth
          margin="normal"
          label="Body"
          name="body"
          multiline
          rows={4}
          value={email.body}
          onChange={handleChange}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSend} color="primary">
          Send
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ComposeEmail;
