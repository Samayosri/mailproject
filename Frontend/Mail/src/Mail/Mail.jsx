import {
  Box,
  Button,
  Stack,
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useEffect, useState } from "react";
import MailContent from "./MailContent";
import axios from "axios"; 
import ComposeEmail from "./ComposeEmail";

function Mail({ folders, selectedFolder }) {
  const [mails, setMails] = useState([]);
  const [selectedMail, setSelectedMail] = useState(null);

  const [isOpen, setIsOpen] = useState(false);

  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  const folderID = folders.find(
    (folder) => folder.folderName === selectedFolder
    
  )?.folderID;
  
  useEffect(() => {
    if (folderID) {
      axios
      .get(`http://localhost:8080/${folderID}`) 
        .then((response) => setMails(response.data))
        .catch((error) => console.error("Error fetching mails:", error));
    }
  }, [folderID]);


  function handleDisplayMail(m) {
    setSelectedMail(m);
  }

  
  function handleCloseDialog() {
    setSelectedMail(null);
  }

  return (
    <Box>
      <Stack spacing={2}>
        {mails.map((m, index) => (
          <Button
            style={{ backgroundColor: "#b89696" }}
            key={index}
            variant="contained"
            sx={{ marginBottom: 1 }}
            onClick={() => handleDisplayMail(m)}
          >
            {m.sender} - {m.subject}
          </Button>
        ))}
      </Stack>

      {selectedMail && (
        <Dialog
          open={Boolean(selectedMail)}
          onClose={handleCloseDialog}
          fullWidth
          maxWidth="md"
          scroll="paper"
        >
          <DialogTitle
            sx={{
              display: "flex",
              alignItems: "center",
              justifyContent: "space-between",
              padding: "16px 24px",
            }}
          >
            <Typography variant="h6">Mail Content</Typography>
            <IconButton
              aria-label="close"
              onClick={handleCloseDialog}
              sx={{
                position: "absolute",
                right: 8,
                top: 8,
              }}
            >
              <CloseIcon />
            </IconButton>
          </DialogTitle>
        <DialogContent dividers sx={{ padding: "16px" }}>
          {selectedFolder === "draft" ? (
            <ComposeEmail onClose={handleClose} open={isOpen} mail={selectedMail} />
          ) : (
            <MailContent
              sender={selectedMail.sender}
              receiver={selectedMail.receiver}
              subject={selectedMail.subject}
              body={selectedMail.body}
            />
          )}
      </DialogContent>

        </Dialog>
      )}
    </Box>
  );
}

export default Mail;
