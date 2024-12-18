import React from 'react';
import { Button } from '@mui/material';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import MailIcon from '@mui/icons-material/Mail';
import SendIcon from '@mui/icons-material/Send'; 
import DraftsIcon from '@mui/icons-material/Drafts';

import axios from 'axios';

function Folder({ setContent,name,setSelectedFolder,selectedFolder,folders ,setMails}) {
   function handleFolderSelected(){
      setSelectedFolder(name);
      setContent("mails");
      const folderID = folders.find(
         (folder) => folder.name === selectedFolder
         
       )?.id;
      
         if (folderID) {
           const fetchMails = async () => {
           console.log("heyyyyyyyyy")
             try {
               const response = await axios.get(`http://localhost:8080/mail/${folderID}`,{
                  params: {
                    pageNumber:0,
                    pageSize:100
                  }
                });
          
               if (response.status === 200) {
                  console.log(response.data);
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
     
   }
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
            onClick={handleFolderSelected}
         >
            {name}
         </Button>
      </>
   );
}

export default Folder;
