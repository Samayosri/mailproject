import React from 'react';
import { Button } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import MailIcon from '@mui/icons-material/Mail';
import SendIcon from '@mui/icons-material/Send'; 

function Folder({ name }) {
   return (
      <>
         <Button
         style={{width:"90%"}}
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
         >
            {name}
         </Button>
      </>
   );
}

export default Folder;
