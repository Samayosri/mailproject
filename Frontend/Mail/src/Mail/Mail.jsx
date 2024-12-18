import {
  Box,
  Button,
  Stack,
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
  Typography,
  Checkbox,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useEffect, useState } from "react";
import MailContent from "./MailContent";
import axios from "axios";
import ComposeEmail from "./ComposeEmail";
function Mail({ folders, selectedFolder, userId, mails ,setTriggerFetch,checkedMails,setCheckedMails}) {


  useEffect(() => {
    setSelectedMail(false)
    
  }, [selectedFolder]);

  const handleCheckboxChange = (mailId) => {
    setCheckedMails((prev) =>
      prev.includes(mailId)
        ? prev.filter((id) => id !== mailId)
        : [...prev, mailId]
    );
  };

  const [selectedMail, setSelectedMail] = useState(null);

  const [isOpen, setIsOpen] = useState(false); // compose

  const handleOpen = () => {
    // compose
    setIsOpen(true);
  };

  const handleClose = () => {
    // compose
    setIsOpen(false);
  };

 
  
  const folderID = folders.find(
    (folder) => folder.folderName === selectedFolder
  )?.folderID;

  function handleDisplayMail(m) {
    handleOpen();
    setSelectedMail(m);
  }

  function handleCloseDialog() {
    setSelectedMail(null);
  }

  return (

    <Box style={{ backgroundColor: "#f4f4f4"}}>
      <Stack spacing={2}>
        {mails.map((mail) => (
          <Box
            key={mail.id}
            sx={{
              display: "flex",
              alignItems: "center",
              backgroundColor: "#e0f7fa",

              padding: 1,
              borderRadius: 1,
              boxShadow: 1,
            }}
          >
            <Checkbox
              checked={checkedMails.includes(mail.id)}
              onChange={() => handleCheckboxChange(mail.id)}
            />
            <Button
              variant="contained"
              sx={{
                flex: 1,
                justifyContent: "flex-start",
                backgroundColor: "#aee7fe",
                color: "black",
                textTransform: "none",
              }}
              onClick={() => handleDisplayMail(mail)}
            >
              <Typography variant="body1" noWrap>
                <strong>{mail.senderMailAddress || "Unknown Sender"}:</strong>{" "}
                {mail.subject || "No Subject"}
              </Typography>
            </Button>
          </Box>
        ))}
      </Stack>
      {selectedMail && selectedFolder !== "Drafts" && (
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
            <MailContent mail={selectedMail} />
          </DialogContent>
        </Dialog>
      )}

      {console.log(selectedFolder)}
      <Box dividers sx={{ padding: "16px" }}>
        {selectedFolder === "Drafts" && selectedMail && (
          <ComposeEmail
            key={selectedMail?.id}
            open={isOpen}
            onClose={handleClose}
            mail={selectedMail}
            userId={userId}
            setTriggerFetch={setTriggerFetch}
          ></ComposeEmail>
        )}
      </Box>
    </Box>
  );
}

export default Mail;
