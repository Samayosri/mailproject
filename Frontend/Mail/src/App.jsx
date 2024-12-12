import React, { useState } from 'react';
import { Box, Grid, Grid2, Paper } from '@mui/material';
import ComposeEmail from './Mail/ComposeEmail';
import MailContent from './Mail/MailContent';
import Mail from './Mail/Mail';
import SideBar from './SideBar/SideBar';
import SearchBar from './SearchBar/SearchBar';
import DivContent from './DivContent/DivContent'
import Registration from './Registration/Registration'
import "./App.css";
import { use } from 'react';

const App = () => {
  const [selectedFolder,setSelectedFolder]=useState("inbox")
  const [selectedMail, setSelectedMail] = useState(null);
  const [signed, setSigned] = useState(false);
  const [window, setWindow] = useState("sign up");

  const [customerDTO, setCustomerDTO] = useState({
   
    id: null,
    role: null,
    points: null,
    name: null,
    mail: null,
    password: null,
    phoneNumber: null,
  });
  const[content,setContent]=useState("mails")
  
  const [folders, setFolders] = useState([
     {  
      id:2,
      name:"Sent",
      userId:1,
     }
     ,
     { 
      id:1,
      name:"Trash",
      userId:5,

     },{
      id:3,
      name:"Drafts",
      userId:6,

     }
  
  ]);

  return (
   
    <>
    {
      window==="sign up"&& <Registration window={window } setWindow={setWindow} customerDTO={customerDTO} setCustomerDTO={setCustomerDTO} signed={signed} setSigned={setSigned}></Registration>
    }
   {
         window =="mail"&&signed && 
         <Box sx={{ flexGrow: 1, height:"100%"}} >
           
            <DivContent content={content}  setContent={setContent} selectedFolder={selectedFolder} folders={folders} setSelectedFolder={setSelectedFolder} setFolders={setFolders}></DivContent>
               
             </Box>
   } 

      
    </>
  
  );
};

export default App;
