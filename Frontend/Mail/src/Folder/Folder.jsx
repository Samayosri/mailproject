import React from 'react';
import { Button } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import MailIcon from '@mui/icons-material/Mail';
import SendIcon from '@mui/icons-material/Send'; 

function Folder({ setContent,name,selectedFolder,setSelectedFolder }) {
   return (
      <>
         <Button 
            
         style={{width:"90%" ,backgroundColor:" #b89696",color:"white"}}
            variant="outlined"
            endIcon={
                name === "sent" ? (
                  <SendIcon />
               ) : name === "trash" ? (
                  <AutoDeleteIcon />
               ) : name === "inbox" ? (
                  <MailIcon />
               ) : (
                  <ArrowForwardIosIcon />
               )
            }
            onClick={()=>{
                setContent("mails");
                setSelectedFolder(name)
            }}
         >
            {name}
         </Button>
      </>
   );
}

export default Folder;
