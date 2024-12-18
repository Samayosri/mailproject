import { Drawer, Box, Stack, Button, IconButton, TextField } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import Folder from "../Folder/Folder";
import { useState, useEffect } from "react";
import SearchBar from "../SearchBar/SearchBar";
import Contacts from "../Contacts/Contacts";
import ComposeEmail from "../Mail/ComposeEmail";
import CreateNewFolderIcon from "@mui/icons-material/CreateNewFolder";
import axios from "axios";

function SideBar({selectedFolder, setSelectedFolder, folders, setFolders, setContent, userId ,setMails}) {

  useEffect(() => {
    const fetchFolders = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/folder/${userId}`);
        if (response.status === 200) {
       
          setFolders(response.data);
          console.log(folders);
        }
      } catch (error) {
        if (error.response?.status === 400) {
          console.error("Error 400:", error.response.data);
        } else {
          console.error("Unexpected Error:", error);

        }
      }
    };
    fetchFolders();
  }, [userId, setFolders]);

  const [isDrawerOpen, setIsDrawerOpen] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [newFolder, setNewFolder] = useState(false);
  const [folderName, setFolderName] = useState("");

  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);
  const handleNewFolder = () => setNewFolder(true);

  const createNewFolder = () => {
    if (folderName) {
      const newFolderObject = { id: 4, name: folderName, userId: 8 };
      setFolders((prevFolders) => [...prevFolders, newFolderObject]);
      setFolderName("");
      setNewFolder(false);
      console.log("New folder created:", folderName);
    }
  };

  return (
    <Box sx={{ position: "relative", width: "100%", display: "flex" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          width: "100%",
        }}
      >
        <Box sx={{ textAlign: "left", mb: 4, mt: 2 }}>
          <IconButton
            size="large"
            edge="start"
            aria-label="open drawer"
            sx={{ mr: 4, color: "black" }}
            onClick={() => setIsDrawerOpen(true)}
          >
            <MenuIcon />
          </IconButton>
        </Box>
        <SearchBar />
        <Contacts setContent={setContent} />
      </div>
      

      {/* Drawer */}
      <Drawer
        variant="temporary"
        open={isDrawerOpen}
        onClose={() => setIsDrawerOpen(false)}
        PaperProps={{
          sx: {
            width: { xs: "200px", sm: "250px" },
            marginTop: { xs: "48px", sm: "56px" },
          },
        }}
      >
        <Stack
          spacing={2}
          sx={{
            justifyContent: "center",
            alignItems: "center",
            padding: 2,
          }}
        >
          <IconButton
            size="large"
            aria-label="create new folder"
            sx={{ color: "black" }}
            onClick={handleNewFolder}
          >
            <CreateNewFolderIcon />
          </IconButton>

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

  {folders && folders.length > 0 ? (
  folders.map((folder) => (
    <Folder
      key={folder.id} 
      name={folder.name}
      setContent={setContent}
      setSelectedFolder={setSelectedFolder}
      selectedFolder={selectedFolder}
      folders={folders}
      setMails={setMails}

    />
  ))
) : (
  <p>No folders available</p>
)}


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
        </Stack>
      </Drawer>

      {isOpen && <ComposeEmail open={isOpen} onClose={handleClose} userId={userId}  />}
    </Box>
  );
}

export default SideBar;
