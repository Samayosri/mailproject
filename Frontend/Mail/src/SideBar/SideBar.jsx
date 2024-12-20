import { Box, Stack, Button, IconButton, TextField } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import Folder from "../Folder/Folder";
import { useState, useEffect } from "react";
import SearchBar from "../SearchBar/SearchBar";
import ComposeEmail from "../Mail/ComposeEmail";
import CreateNewFolderIcon from "@mui/icons-material/CreateNewFolder";
import axios from "axios";
import ContactsButton from "../Contacts/ContactsButton";
import AccountCircleIcon from '@mui/icons-material/AccountCircle'
import LogoutIcon from '@mui/icons-material/Logout';
function SideBar({setCheckedMails,handleMove,contacts,selectedFolder, setSelectedFolder, folders, setFolders, setContent, userId ,setMails,setTriggerFetch,handleLogout,userName}) {
  const [isOpen, setIsOpen] = useState(false);
  const [newFolder, setNewFolder] = useState(false);
  const [folderName, setFolderName] = useState("");

  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  useEffect(() => {
    const fetchFolders = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/folder/${userId}`);
        if (response.status === 200) {
          setFolders(response.data);
        }
      } catch (error) {
        console.error("Error fetching folders:", error);
      }
    };
    setSelectedFolder("Inbox")
    fetchFolders();
    
  }, [userId, setFolders]);

  const createNewFolder = async () => {
    if (folderName) {
      const newFolderObject = { id: null, name: folderName, userId: userId };
      try {
        const response = await axios.post(`http://localhost:8080/folder/create`, newFolderObject);
        if (response.status === 201) {
          setFolders((prevFolders) => {
            const updatedFolders = [...prevFolders, response.data];
            console.log("Updated folders:", updatedFolders); // Logging the updated list of folders
            return updatedFolders;
          });
          setFolderName("");
          setNewFolder(false);
        }
      } catch (error) {
        console.error("Error creating folder:", error);
      }
    }
  };
  useEffect(() => {
    console.log("Folders updated:", folders);
  }, [folders]);
  
  

  return (
    <Box
      sx={{
        width: 250,
        position: "fixed",
        top: 0,
        left: 0,
        bottom: 0,
        backgroundColor: "#f4f4f4",
        padding: 2,
        overflowY: "auto",
        display: "flex",
        flexDirection: "column",
      }}
    >
      {/* Folder creation and search bar */}
      <Stack spacing={2} sx={{ alignItems: "center", marginBottom: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" width="100%">
  <Box display="flex" alignItems="center">
    <AccountCircleIcon style={{ background: "white", padding: "5px", borderRadius: "50%", marginRight: "10px" }} />
    <span style={{ fontWeight: "bold", fontSize: "18px", color: "#333" }}>{userName}</span>
  </Box>
  <IconButton onClick={handleLogout} title="Logout">
    <LogoutIcon style={{ color: "black" }} />
  </IconButton>
</Box>
      <div style={{display:"flex",justifyContent:"space-between",alignItems:"center"}}>
      <label htmlFor="">Create new folder</label>
        <IconButton
          size="large"
          aria-label="create new folder"
          onClick={() => setNewFolder(true)}
        >
          <CreateNewFolderIcon style={{background:""}} />
        </IconButton>
      </div>

           <Button
            variant="contained"
            onClick={handleOpen}
            sx={{
              width: "90%",
              backgroundColor: "#aee7fe",
              color: "black",
            }}
          >
            New mail
          </Button>
          {isOpen && <ComposeEmail setCheckedMails={setCheckedMails} handleMove={handleMove} contacts={contacts} open={isOpen} onClose={handleClose} userId={userId} setTriggerFetch={setTriggerFetch} />}

        {newFolder && (
          <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <TextField
              label="Folder Name"
              variant="outlined"
              value={folderName}
              onChange={(e) => setFolderName(e.target.value)}
            />
            <Stack direction="row" spacing={1} sx={{ marginTop: 1 }}>
              <Button variant="contained" color="success" onClick={createNewFolder}>
                Create
              </Button>
              <Button variant="outlined" color="error" onClick={() => setNewFolder(false)}>
                Cancel
              </Button>
            </Stack>
          </Box>
        )}

        {/* Folders list */}
        {folders && folders.length > 0 ? (
          folders.map((folder) => (
            <Folder
              key={folder.id}
              folder={folder}
              setContent={setContent}
              setSelectedFolder={setSelectedFolder}
              selectedFolder={selectedFolder}
              folders={folders}
              setMails={setMails}
              setFolders={setFolders}
              setTriggerFetch={setTriggerFetch}
             
            />
          ))
        ) : (
          <p>No folders available</p>
        )}
      </Stack>

      {/* Search bar and contacts button */}
    </Box>
  );
}

export default SideBar;

