import React, { useEffect, useState } from "react";
import axios from "axios";
import RecentActorsIcon from "@mui/icons-material/RecentActors";
import {
  Button,
  TextField,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Box,
  Snackbar,
  Alert,
} from "@mui/material";
import RefreshIcon from '@mui/icons-material/Refresh';
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import AddIcon from "@mui/icons-material/Add";

function MyContacts({ userId ,contacts,setContacts}) {
 // List of contacts

  const [open, setOpen] = useState(false); // Dialog visibility
  const [currentContact, setCurrentContact] = useState({
    ownerId: userId,
    name: "",
    emailAddress: [],
    id: null,
  }); // Contact for editing/adding
  const [editIndex, setEditIndex] = useState(null); // To track editing
  const [tempEmails, setTempEmails] = useState(""); // Temporary string for email input
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("success");

  // Fetch contacts from backend when component loads
  useEffect(() => {
    fetchContacts();
  }, []);


  // Fetch contacts from backend
  const fetchContacts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/contact/${userId}`);
      
      setContacts(response.data);
      console.log(response.data)
    } catch (error) {
      setSnackbarSeverity("error");
      setSnackbarMessage(error.response.data);
      setOpenSnackbar(true);
    }
  };
  // Handle Delete Contact
  const handleDelete = async (index) => {
    const contactToDelete = contacts[index];
    try {
      await axios.delete(`http://localhost:8080/contact/${contactToDelete.id}`);
      const updatedContacts = contacts.filter((_, i) => i !== index);
      setContacts(updatedContacts);

      // Show success snackbar
      setSnackbarSeverity("success");
      setSnackbarMessage("Contact deleted successfully!");
      setOpenSnackbar(true);
    } catch (error) {
      console.error("Error deleting contact:", error);
      setSnackbarSeverity("error");
      setSnackbarMessage(`Error deleting contact: ${error.message}`);
      setOpenSnackbar(true);
    }
  };

  // Handle Open Dialog for Add or Edit
  const handleOpenDialog = (
    contact = { ownerId: userId, name: "", emailAddress: [], id: null },
    index = null
  ) => {
    setCurrentContact(contact);
    setEditIndex(index);
    setTempEmails(contact.emailAddress.join(" ")); // Convert list to string
    setOpen(true);
  };

  // Handle Save Contact
  // Handle Save Contact
const handleSave = async () => {
  // Validation for empty fields
  if (!currentContact.name.trim() || !tempEmails.trim()) {
    setSnackbarSeverity("error");
    setSnackbarMessage("Name and Emails cannot be empty!");
    setOpenSnackbar(true);
    return;
  }

  try {
    const updatedContact = {
      ...currentContact,
      emailAddress: tempEmails
        .split(" ") // Split by spaces
        .map((email) => email.trim()) // Trim each email
        .filter((email) => email.length > 0), // Remove empty strings
    };

    if (updatedContact.emailAddress.length === 0) {
      setSnackbarSeverity("error");
      setSnackbarMessage("Emails cannot be empty or invalid!");
      setOpenSnackbar(true);
      return;
    }

    if (editIndex !== null) {
      // Editing an existing contact
      await axios.put(
        `http://localhost:8080/contact/${contacts[editIndex].id}`,
        updatedContact
      );
      const updatedContacts = [...contacts];
      updatedContacts[editIndex] = updatedContact;
      setContacts(updatedContacts);
      setSnackbarSeverity("success");
      setSnackbarMessage("Contact updated successfully!");
    } else {
      // Adding a new contact
      const response = await axios.post(
        "http://localhost:8080/contact",
        updatedContact
      );
      setContacts([...contacts, response.data]);
      setSnackbarSeverity("success");
      setSnackbarMessage("Contact added successfully!");
    }

    setOpen(false);
    setCurrentContact({ ownerId: userId, name: "", emailAddress: [], id: null });
    setEditIndex(null);
    setTempEmails("");
    setOpenSnackbar(true);
  } catch (error) {
    console.error("Error saving contact:", error);
    setSnackbarSeverity("error");
    setSnackbarMessage(error.response.data);
    setOpenSnackbar(true);
  }
};

  return (
    <>
      {/* Snackbar for notifications */}
      <Snackbar
        open={openSnackbar}
        autoHideDuration={2000}
        onClose={() => setOpenSnackbar(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={() => setOpenSnackbar(false)}
          severity={snackbarSeverity}
          variant="filled"
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>

      {/* Add Contact Button */}
      <IconButton onClick={fetchContacts}>
           <RefreshIcon />
         </IconButton>
      <Button
        variant="contained"
        color="primary"
        startIcon={<AddIcon />}
        onClick={() => handleOpenDialog()}
        style={{ marginTop: "10px" }}
      >
        Add Contact
      </Button>

      {/* Contacts List */}
      <List>
        {contacts.map((contact, index) => (
          <ListItem key={index} button>
            <ListItemText
              primary={contact.name}
              secondary={`Emails: ${contact.emailAddress.join(" ")}`}
            />
            <Box sx={{ display: "flex", alignItems: "center", gap: 1, ml: "auto" }}>
              {/* Edit Button */}
              <IconButton
                sx={{ color: "dodgerblue" }}
                edge="end"
                aria-label="edit"
                onClick={() => handleOpenDialog(contact, index)}
              >
                <EditIcon />
              </IconButton>
              {/* Delete Button */}
              <IconButton
                sx={{ color: "dodgerblue" }}
                edge="end"
                aria-label="delete"
                onClick={() => handleDelete(index)}
              >
                <DeleteIcon />
              </IconButton>
            </Box>
          </ListItem>
        ))}
      </List>

      {/* Add/Edit Dialog */}
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editIndex !== null ? "Edit Contact" : "Add Contact"}</DialogTitle>
        <DialogContent>
          <TextField
            label="Name"
            fullWidth
            value={currentContact.name}
            onChange={(e) =>
              setCurrentContact({ ...currentContact, name: e.target.value })
            }
            margin="dense"
          />
          <TextField
            label="Emails (space-separated)"
            fullWidth
            value={tempEmails}
            onChange={(e) => setTempEmails(e.target.value)} // Temporary input for email list
            margin="dense"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSave} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default MyContacts;
