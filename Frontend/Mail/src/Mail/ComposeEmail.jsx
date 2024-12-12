import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  TextField,
  DialogActions,
  Button,
  IconButton,
  Typography,
} from '@mui/material';
import InputFileUpload from './InputFileUpload'; // Assuming InputFileUpload is in a separate file

const ComposeEmail = ({ open, onClose }) => {
  const [email, setEmail] = useState({
    subject: '',
    to: '',
    body: '',
    attachments: [],
  });


  const handleChange = (e) => {
    setEmail({ ...email, [e.target.name]: e.target.value });
  };

  const handleSend = () => {
    console.log('Email sent:', email);
    handleClose();
  };
  
  const handleClose = () => { setEmail({ subject: '', to: '', body: '', attachments: [], }); onClose(); };


  const handleFileChange = (event) => {
    setEmail({
      ...email,
      attachments: [...email.attachments, ...event.target.files],
    });
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
            label="To"
            name="to"
            value={email.to}
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
        <InputFileUpload onChange={handleFileChange} multiple />
        {email.attachments.length > 0 && (
          <div style={{padding:5}}>
          <ul style={{ paddingLeft: 0, margin: 2,padding : 2 , color: 'grey',textAlign : 'left'}}> {/* Remove default styling */}
            {email.attachments.map((file) => (
              <li key={file.name}>{file.name}</li>
            ))}
          </ul>
        </div>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>
        <Button onClick={handleSend} color="primary">
          Send
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ComposeEmail;