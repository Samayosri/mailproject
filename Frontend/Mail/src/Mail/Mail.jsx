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
import { WidthFull } from "@mui/icons-material";

function Mail({ folders, selectedFolder ,userId}) {
  const [checkedMails, setCheckedMails] = useState([]);
  
  const handleCheckboxChange = (mailId) => {
    setCheckedMails((prev) =>
      prev.includes(mailId)
        ? prev.filter((id) => id !== mailId)
        : [...prev, mailId]
    );
  };
  const [mails, setMails] = useState([
  ]);
  const [selectedMail, setSelectedMail] = useState(null);

  const [isOpen, setIsOpen] = useState(false); // compose

  const handleOpen = () => { // compose
    setIsOpen(true);
  };

  const handleClose = () => { // compose
    setIsOpen(false);
  };

  function handleMove(){
    //send request to move to another folder
  }
  function handleDelete(){
    
  }
  const folderID = folders.find(
    (folder) => folder.folderName === selectedFolder
    
  )?.folderID;
  
  useEffect(() => {
    if (folderID) {
      const fetchMails = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/mail/${userId}`);
          if (response.status === 200) {
            setMails(response.data);
          }
        } catch (error) {
          if (error.response?.status === 400) {
            console.error("Error 400:", error.response.data);
          } else {
            console.error("Unexpected Error:", error);
  
          }
        }
      };
      fetchMails();
    }
  }, [folderID]);

 
  
  function handleDisplayMail(m) {
    handleOpen()
    setSelectedMail(m);
  }

  
  function handleCloseDialog() {
    setSelectedMail(null);
  }

  return (
    <Box>
      <Box style={{
   
        display:"flex",
        flexDirection:"column",
        position:"absolute",
        right:"1%",
        gap:"10px"

      }}>
      <Button variant="contained"
      
    >Move</Button>
    <Button variant="outlined" color="error"

    >
     
  Delete
   </Button>
      </Box>

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
                <strong>{mail.senderEmailAddress || "Unknown Sender"}:</strong> {mail.subject || "No Subject"}
              </Typography>
            </Button>
          </Box>
        ))}
      </Stack>
      {(selectedMail && selectedFolder !== "Drafts")&&(
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
          <MailContent
              mail={selectedMail}
            />
        </DialogContent>
        </Dialog>  )}
      
       
      {console.log(selectedFolder)}
      <Box dividers sx={{ padding: "16px" }}>
        {(selectedFolder === "Drafts"&&selectedMail)&&(
               <ComposeEmail open={isOpen} onClose={handleClose} mail={selectedMail } userId={userId} method={"draft"}></ComposeEmail>
          ) 
         
          }
        </Box>
          
  
    </Box>
  );
}

export default Mail;
