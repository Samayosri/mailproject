import React, { useState } from "react";
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
  Snackbar,
  Alert,
} from "@mui/material";
import axios from "axios";
import VisibilityIcon from "@mui/icons-material/Visibility";
import DownloadIcon from "@mui/icons-material/Download";
import DeleteIcon from "@mui/icons-material/Delete";
import InputFileUpload from "./InputFileUpload";
import SelectContacts from "./SelectContacts";

const ComposeEmail = ({handleMove,setCheckedMails, contacts,open, onClose, mail = {}, userId ,setTriggerFetch  }) => {
  console.log(mail)
  const mailDto = {
    id: mail.id || null,
    senderId: userId,
    subject: mail.subject || "",
    senderMailAddress: mail.senderMailAddress || "",
    toReceivers: mail.toReceivers || [],
    ccReceivers: mail.ccReceivers || [],
    bccReceivers: mail.bccReceivers || [],
    body: mail.body || "",
    importance: mail.importance || 3,
    attachments: mail.attachments || [],
    creationDate: mail.creationDate || null,
    folder : mail.folder || "ghost",
    name : mail.name || "ghost"
  };

  const [email, setEmail] = useState(mailDto);
  const [toInput, setToInput] = useState(email.toReceivers.join(" "));
  const [ccInput, setCcInput] = useState(email.ccReceivers.join(" "));
  const [bccInput, setBccInput] = useState(email.bccReceivers.join(" "));
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("success");
  const handleMove1= async () => {
    const folderID = mail.folderId;
    if (!folderID) {
      console.error("Invalid folder selected.");
      return;
    }
    const moveMails = {
      userId,
      destinationFolderId: folderID,
      mailIds: mail,
    };
    console.log(moveMails);

    try {
      const response = await axios.put("http://localhost:8080/mail/move", moveMails);

      if (response.status === 200) {
        setCheckedMails([]);
        console.log("Mails moved successfully");
      }
    } catch (error) {
      console.error("Error moving mails:", error);
    }

 
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
   
    if (name === "toInput") {
      setToInput(value);
      setEmail({
        ...email,
        toReceivers: value.split(/\s+/).filter(Boolean),
      });
    } else if (name === "ccInput") {
      setCcInput(value);
      setEmail({
        ...email,
        ccReceivers: value.split(/\s+/).filter(Boolean),
      });
    } else if (name === "bccInput") {
      setBccInput(value);
      setEmail({
        ...email,
        bccReceivers: value.split(/\s+/).filter(Boolean),
      });
    } else {
      setEmail({
        ...email,
        [name]: value,
      });
    }
  };

  const handleSend = async () => {
    let errorMessage = "";
    if (!email.toReceivers.length) {
      errorMessage = "Please fill out the 'To' field.";
    } else if (!email.subject.trim()) {
      errorMessage = "Please fill out the 'Subject' field.";
    } 
    if (errorMessage) {
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
      return;
    }

    const url = "http://localhost:8080/mail/send"

    try {
      console.log(email)
      const response = await axios.post(url, email);

      if (response.status === 201) {
        setSnackbarMessage("Email sent successfully!");
        setSnackbarSeverity("success");
        triggerSnackbarAndClose();
        console.log("mail send");
          
      }
    } catch (error) {
      if (error.response?.status === 400) {
        // to or bcc errors
        setSnackbarMessage(error.response.data);
        setSnackbarSeverity("error");
        setOpenSnackbar(true);
        return;
      } else {
        console.error("Unexpected Error:", error);
      }
    }

    setTriggerFetch(true);
   
  };

  const triggerSnackbarAndClose = () => {
    setOpenSnackbar(true);
    setTimeout(() => {
      onClose(false);
      setOpenSnackbar(false);
    }, 1500);
  };

  const handleClose = async () => {

    const url = "http://localhost:8080/mail/draft"

    try {
      const response = await axios.post(url, email);

      if (response.status === 201) {
        handleMove1();
        setSnackbarMessage("Email saved to drafts.");
        setSnackbarSeverity("info");
        triggerSnackbarAndClose();
        console.log("drafted")
      }
    } catch (error) {
      if (error.response?.status === 400) {
        setSnackbarMessage(error.response.data);
        setSnackbarSeverity("error");
        setOpenSnackbar(true);
        return;
      } else {
        console.error("Unexpected Error:", error);
      }
    }

    setTriggerFetch(true);

  };

  const handleFileChange = (event) => {
    const files = event.target.files;

    Array.from(files).forEach((file) => {
      const reader = new FileReader();
      reader.onload = () => {
        const attachment = {
          name: file.name,
          type: file.type,
          file: reader.result, // Base64 string of the file content
          id : null
        };

        // Add the new attachment to the email state
        setEmail((prevEmail) => ({
          ...prevEmail,
          attachments: [...prevEmail.attachments, attachment],
        }));
      };
      reader.readAsDataURL(file); // Convert file to Base64 string
    });
  };
const onAddContactTo=(contact) => {
  const updatedToReceivers = [...email.toReceivers, contact];
  setEmail({ ...email, toReceivers: updatedToReceivers });
  setToInput(updatedToReceivers.join(" "));
};
const onAddContactBcc = (contact) => {
  const updatedBccReceivers = [...email.bccReceivers, contact];
  setEmail({ ...email, bccReceivers: updatedBccReceivers });
  setBccInput(updatedBccReceivers.join(" "));
};
const onAddContactCc = (contact) => {
  const updatedCcReceivers = [...email.ccReceivers, contact];
  setEmail({ ...email, ccReceivers: updatedCcReceivers });
  setCcInput(updatedCcReceivers.join(" "));
};

  const handleViewAttachment = (attachment) => {
    const { file: base64File, name, type } = attachment;
    const byteString = atob(base64File.split(",")[1]);
    const byteNumbers = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
      byteNumbers[i] = byteString.charCodeAt(i);
    }
    const file = new File([byteNumbers], name, { type });
    const fileURL = URL.createObjectURL(file);
    window.open(fileURL, "_blank");
  };

  const handleDownloadAttachment = (attachment) => {
    const link = document.createElement("a");
    link.href = attachment.file;
    link.download = attachment.name;
    link.click();
  };

  const handleDeleteAttachment = (fileIndex) => {
    setEmail({
      ...email,
      attachments: email.attachments.filter((_, index) => index !== fileIndex),
    });
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
      <DialogTitle>Compose Email</DialogTitle>
      <DialogContent>
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
        <TextField
          fullWidth
          margin="normal"
          label="Subject"
          name="subject"
          value={email.subject}
          onChange={handleChange}
        />
        <div style={{display:"flex",alignItems:"center"}}>
        <TextField
          fullWidth
          margin="normal"
          label="To space separeated"
          name="toInput"
          value={toInput}
          onChange={handleChange}
        />
  <SelectContacts
  contacts={contacts}
    onAddContact={onAddContactTo}
  />
        </div>
        <div style={{display:"flex",alignItems:"center"}}>
        <TextField
          fullWidth
          margin="normal"
          label="CC"
          name="ccInput"
          value={ccInput}
          onChange={handleChange}
        />
  <SelectContacts
  contacts={contacts}
    onAddContact={onAddContactCc}
  />
        </div>
      
        <div style={{display:"flex",alignItems:"center"}}>
        <TextField
          fullWidth
          margin="normal"
          label="BCC"
          name="bccInput"
          value={bccInput}
          onChange={handleChange}
        />
  <SelectContacts
  contacts={contacts}
    onAddContact={onAddContactBcc}
  />
        </div>
       
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
            <MenuItem value={1}>1 - High</MenuItem>
            <MenuItem value={2}>2 - Moderate</MenuItem>
            <MenuItem value={3}>3 - Normal</MenuItem>
            <MenuItem value={4}>4 - Low</MenuItem>
          </Select>
        </FormControl>
        <InputFileUpload onChange={handleFileChange} multiple />
        {email.attachments.length > 0 && (
          <div style={{ padding: 5 }}>
            <ul style={{ listStyle: "none", padding: 0, margin: 2 }}>
              {email.attachments.map((attachment, index) => (
                <li
                  key={index}
                  style={{ display: "flex", alignItems: "center", marginBottom: 5 }}
                >
                  <span style={{ flex: 1 }}>{attachment.name}</span>
                  <IconButton
                    size="small"
                    onClick={() => handleViewAttachment(attachment)}
                  >
                    <VisibilityIcon sx={{ color: "dodgerblue" }} />
                  </IconButton>
                  <IconButton
                    size="small"
                    onClick={() => handleDownloadAttachment(attachment)}
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