import Mail from "../Mail/Mail";
import SideBar from "../SideBar/SideBar";
import { Box, Button, Stack } from "@mui/material";

function DivContent({content, setContent, selectedFolder, folders, setFolders, setSelectedFolder, userId}) {
  return (
    <>
      <SideBar 
        folders={folders} 
        selectedFolder={selectedFolder} 
        setSelectedFolder={setSelectedFolder} 
        setFolders={setFolders}
        setContent={setContent} 
        userId={userId} 
      />
      <div style={{width: "80%", margin: "auto", background: "white", height: "100%"}}>
        {content === "mails" && <Mail folders={folders} selectedFolder={selectedFolder} userId={userId} />}
        
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
