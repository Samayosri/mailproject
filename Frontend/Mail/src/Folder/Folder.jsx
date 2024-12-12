import React from 'react';
import { Button } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import MailIcon from '@mui/icons-material/Mail';
import SendIcon from '@mui/icons-material/Send'; 

function Folder({ setContent,name,selectedFolder,setSelectedFolder }) {
   function handleFolderSelected(){
      setContent("mails");
   }
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
               ) : name==="draft"?(
                  <ArrowForwardIosIcon />
               ):
               (
                  <ArrowForwardIosIcon />
               )
            }
            onClick={()=>{
              
                setSelectedFolder(name)
            }}
         >
            {name}
         </Button>
      </>
   );
}

export default Folder;
