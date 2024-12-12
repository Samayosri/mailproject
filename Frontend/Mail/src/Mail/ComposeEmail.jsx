import React, { useState, useEffect } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  TextField,
  DialogActions,
  Button,
  IconButton,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import DownloadIcon from "@mui/icons-material/Download";
import DeleteIcon from "@mui/icons-material/Delete";
import InputFileUpload from "./InputFileUpload";

const ComposeEmail = ({ open, onClose, mail = {} }) => {
  const defaultEmail = {
    id: mail.id || null,
    senderId: mail.senderId || null,
    subject: mail.subject || "",
    senderEmailAddress: mail.senderEmailAddress || "",
    to: mail.to || "",
    body: mail.body || "",
    importance: mail.importance || null,
    attachments: mail.attachments || [],
    creationDate: mail.creationDate || "",
  };

  const [email, setEmail] = useState(defaultEmail);

  const handleChange = (e) => {
    setEmail({ ...email, [e.target.name]: e.target.value });
  };

  const handleSend = () => {
    console.log("Email sent:", email);
    handleClose();
  };

  const handleClose = () => {
    setEmail(defaultEmail); // Reset to default state
    onClose();
  };

  const handleFileChange = (event) => {
    setEmail({
      ...email,
      attachments: [...email.attachments, ...event.target.files],
    });
  };

  const handleViewAttachment = (file) => {
    // Logic to view attachment (e.g., open in a new tab)
    const fileURL = URL.createObjectURL(file);
    window.open(fileURL, "_blank");
  };

  const handleDownloadAttachment = (file) => {
    // Logic to download attachment
    const link = document.createElement("a");
    link.href = URL.createObjectURL(file);
    link.download = file.name;
    link.click();
  };

  const handleDeleteAttachment = (fileIndex) => {
    setEmail({
      ...email,
      attachments: email.attachments.filter((_, index) => index !== fileIndex),
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
        <FormControl fullWidth margin="normal">
          <InputLabel id="importance-label">Importance</InputLabel>
          <Select
            labelId="importance-label"
            name="importance"
            value={email.importance}
            onChange={handleChange}
            label="Importance"
          >
            <MenuItem value={1}>1 important </MenuItem>
            <MenuItem value={2}>2 moedrate </MenuItem>
            <MenuItem value={3}>3 normal </MenuItem>
            <MenuItem value={4}>4 Lowest </MenuItem>
          </Select>
        </FormControl>
        <InputFileUpload onChange={handleFileChange} multiple />
        {email.attachments.length > 0 && (
          <div style={{ padding: 5 }}>
            <ul style={{ listStyle: "none", padding: 0, margin: 2 }}>
              {Array.from(email.attachments).map((file, index) => (
                <li
                  key={index}
                  style={{
                    display: "flex",
                    alignItems: "center",
                    marginBottom: 5,
                  }}
                >
                  <span style={{ flex: 1 }}>{file.name}</span>
                  <IconButton
                    size="small"
                    onClick={() => handleViewAttachment(file)}
                  >
                    <VisibilityIcon sx={{ color: "dodgerblue" }} />
                  </IconButton>
                  <IconButton
                    size="small"
                    onClick={() => handleDownloadAttachment(file)}
                  >
                    <DownloadIcon sx={{ color: "dodgerblue" }} />
                  </IconButton>
                  <IconButton
                    size="small"
                    onClick={() => handleDeleteAttachment(index)}
                  >
                    <DeleteIcon sx={{ color: "dodgerblue" }} />
                  </IconButton>
                </li>
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
