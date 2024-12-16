import React from 'react';
import { Button } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import MailIcon from '@mui/icons-material/Mail';
import SendIcon from '@mui/icons-material/Send'; 
import DraftsIcon from '@mui/icons-material/Drafts';
import ComposeEmail from '../Mail/ComposeEmail';
function Folder({ setContent,name,selectedFolder,setSelectedFolder }) {


   return (
 
      <>
         <Button 
            
         style={{width:"90%" ,backgroundColor:" #aee7fe",color:"black"}}
            
            variant="contained"
            endIcon={
                name === "Sent" ? (
                  <SendIcon />
               ) : name === "Trash" ? (
                  <AutoDeleteIcon />
               ) : name === "Inbox" ? (
                  <MailIcon />
               ) : name==="Drafts"?(
                   <DraftsIcon/>
               ):
               (
                  <ArrowForwardIosIcon />
               )
            }
            onClick={()=>{
               setSelectedFolder(name);
               setContent("mails");
               
            }}
         >
            {name}
         </Button>
      </>
   );
}

export default Folder;