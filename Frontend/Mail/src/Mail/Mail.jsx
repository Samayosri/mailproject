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
import { useEffect, useState ,useCallback} from "react";
import MailContent from "./MailContent";
import axios from "axios";
import ComposeEmail from "./ComposeEmail";
import { use } from "react";

function Mail({handleMove, contacts, folders, selectedFolder, userId, mails, setTriggerFetch, checkedMails, setCheckedMails ,setMails}) {
  useEffect(() => {
    console.log(mails);
     setSelectedMail(false);
  }, [selectedFolder]);

  const handleCheckboxChange = useCallback(
    (mailId) => {
      setCheckedMails((prev) =>
        prev.includes(mailId)
          ? prev.filter((id) => id !== mailId)
          : [...prev, mailId]
      );
    },
    [setCheckedMails]
  );
  

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
    console.log(m);
    handleOpen();
    setSelectedMail(m);
  }

  function handleCloseDialog() {
    setSelectedMail(null);
  }

  return (
    <Box style={{ backgroundColor: "#f4f4f4" }}>
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
              <strong>{mail.folder === "Draft" ? "Draft : " : ""}{mail.subject}</strong>
              </Typography>
            </Button>
          </Box>
        ))}
      </Stack>
      {selectedMail &&selectedFolder!== "Drafts" && (
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

    
      <Box dividers sx={{ padding: "16px" }}>
        {selectedMail && selectedFolder === "Drafts" && (
          <ComposeEmail
          handleMove={handleMove}
            contacts={contacts}
            key={selectedMail?.id}
            open={isOpen}
            onClose={handleClose}
            mail={selectedMail}
            userId={userId}
            setTriggerFetch={setTriggerFetch}
            setCheckedMails={setCheckedMails}
          ></ComposeEmail>
        )}
      </Box>
      
    </Box>
  );
}

export default Mail;