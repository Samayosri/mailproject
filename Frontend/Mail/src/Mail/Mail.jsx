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
import { useState } from "react";
import MailContent from "./MailContent";

function Mail({ folders, selectedFolder }) {
  let listOfmails;
  folders.forEach((element) => {
    if (element.folderName === selectedFolder) {
      listOfmails = element.mails;
    }
  });

  const [selectedMail, setSelectedMail] = useState(null);

  // Handle mail selection
  function handleDisplayMail(m) {
    setSelectedMail(m);
  }

  // Handle dialog close
  function handleCloseDialog() {
    setSelectedMail(null);
  }

  // Map emails to buttons
  const mail = listOfmails.map((m, index) => (
    <Button
      style={{ backgroundColor: " #b89696" }}
      key={index}
      variant="contained"
      sx={{ marginBottom: 1 }}
      onClick={() => handleDisplayMail(m)}
    >
      {m.sender} - {m.subject}
    </Button>
  ));

  return (
    <>
      <Box>
        <Stack spacing={2}>{mail}</Stack>

        {/* Dialog to display selected mail */}
        {selectedMail && (
          <Dialog
            open={Boolean(selectedMail)} // Show dialog if a mail is selected
            onClose={handleCloseDialog}
            fullWidth
            maxWidth="md" // Adjust size of the popup
            scroll="paper" // Enable scrolling
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
            <DialogContent
              dividers // Adds a scrollable paper style
              sx={{
                padding: "16px",
              }}
            >
              {/* Display mail content */}
              <MailContent
                sender={selectedMail.sender}
                receiver={selectedMail.receiver}
                subject={selectedMail.subject}
                body={selectedMail.body}
              />
            </DialogContent>
          </Dialog>
        )}
      </Box>
    </>
  );
}

export default Mail;
