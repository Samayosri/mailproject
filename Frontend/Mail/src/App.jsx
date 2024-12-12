import React, { useState } from 'react';
import { Box, Grid, Grid2, Paper } from '@mui/material';
import ComposeEmail from './Mail/ComposeEmail';
import MailContent from './Mail/MailContent';
import Mail from './Mail/Mail';
import SideBar from './SideBar/SideBar';
import SearchBar from './SearchBar/SearchBar';
import DivContent from './DivContent/DivContent'
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

  // Mails data state, storing actual mail data
  
  const [folders, setFolders] = useState([
     { 
      folderName:"inbox",
      mails:[
        {
          sender: "Ahmed",
          receiver: "Noor",
          subject: "React JS",
        
          body: "Hello, first mail"
        }
        , {
          sender: "John",
          receiver: "Sara",
          subject: "Project Update",
    
          body: "Please check the latest update on the project."
        }
      ]
     },
     {
      folderName:"trash",
      mails:[
        {
          sender: "sama",
          receiver: "samaa",
          subject: "spring ",
          body: "Hello, first mail"
        }
        , {
          sender: "menna",
          receiver: "Sara",
          subject: "thank u",
          body: "Please check the latest update on the project."
        }
      ]
     },
     {
      folderName:"trash",
      mails:[
        {
          sender: "sama",
          receiver: "samaa",
          subject: "spring ",
          body: "Hello, first mail"
        }
        , {
          sender: "menna",
          receiver: "Sara",
          subject: "thank u",
          body: "Please check the latest update on the project."
        }
      ]
     },

    {
      folderName:"sent",
      mails:[
        {
          sender: "sama",
          receiver: "samaa",
          subject: "spring ",
          body: "Hello, first mail"
        }
        , {
          sender: "menna",
          receiver: "Sara",
          subject: "thank u",
          body: "Please check the latest update on the project."
        }
      ]
     }
   
  ]);

  return (
    <>

<Box sx={{ flexGrow: 1, height:"100%"}} >
  
   <DivContent content={content}  setContent={setContent} selectedFolder={selectedFolder} folders={folders} setSelectedFolder={setSelectedFolder}></DivContent>
      
    </Box>
      
    </>
  
  );
};

export default App;
