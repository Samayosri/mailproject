import Mail from "../Mail/Mail";
import SideBar from "../SideBar/SideBar";
import { Box, Button, Stack } from "@mui/material";
import { useState } from "react";
function DivContent({content, setContent, selectedFolder, folders, setFolders, setSelectedFolder, userId}) {
  const [mails, setMails] = useState([
  ]);
  return (
    <>
      <SideBar 
        folders={folders} 
        selectedFolder={selectedFolder} 
        setSelectedFolder={setSelectedFolder} 
        setFolders={setFolders}
        setContent={setContent} 
        userId={userId} 
        setMails={setMails}
      />
      <div style={{width: "80%", margin: "auto", background: "white", height: "100%"}}>
        {content === "mails" && <Mail  
        folders={folders} selectedFolder={selectedFolder} userId={userId} mails={mails} />}
        
        {content === "contacts" && (
          <Stack>
            <h1 style={{color: "black"}}>samaa</h1>
          </Stack>
        )}
      </div>
    </>
  );
}

export default DivContent;
