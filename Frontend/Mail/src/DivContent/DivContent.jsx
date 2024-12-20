import { useState, useEffect } from "react";
import axios from "axios";
import RefreshIcon from '@mui/icons-material/Refresh';
import { 
  Box, IconButton, FormControl, InputLabel, Select, MenuItem, 
  Stack, Button, Dialog, DialogTitle, DialogContent, DialogActions 
} from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import SideBar from "../SideBar/SideBar";
import Mail from "../Mail/Mail";
import MyContacts from "../Contacts/MyContacts";
import SearchBar from "../SearchBar/SearchBar";
import ContactsButton from "../Contacts/ContactsButton";
import Filter from "../SearchBar/Filter";
import SearchIcon from '@mui/icons-material/Search';
import AutoDeleteIcon from '@mui/icons-material/AutoDelete';
import DriveFileMoveIcon from '@mui/icons-material/DriveFileMove';
import {


 
  Snackbar,
  Alert,
} from "@mui/material";

function DivContent({
  content,
  setContent,
  selectedFolder,
  folders,
  setFolders,
  setSelectedFolder,
  userId,
  userName,
  handleLogoutApp
}) {
  const [contacts, setContacts] = useState([]);
  const [currentMails, setCurrentMails] = useState([]);
  const [page, setPage] = useState(0);
  const [sortBy, setSortBy] = useState("");
  const [checkedMails, setCheckedMails] = useState([]);
  const [triggerFetch, setTriggerFetch] = useState(false);
  const [moveDialogOpen, setMoveDialogOpen] = useState(false);
  const [selectedMoveFolder, setSelectedMoveFolder] = useState("");
  const [selectedFilters,setSelectedFilters] = useState([]);
  const [searchFolder,setSearchFolder] = useState(false);
  const [searchWord,setSearchWord] = useState("");
  const [searching,setSearching]  = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("success");
  useEffect(()=>{
    fetchMails(0);
    fetchContacts();
  },[]
  )
  const fetchContacts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/contact/${userId}`);
      
      setContacts(response.data);
      console.log(response.data)
    } catch (error) {
     console.log(error);
   
    }
  };
  useEffect(()=>{
    let supportedFilters = content==="contacts"?["name","emailAddress"] :  [
      "attachments",
      "body",
      "date",
      "importance",
      "receiversEmailAddress",
      "receiversName",
      "subject",
      "senderName",
      "senderEmailAddress",
    ];
    setSelectedFilters(supportedFilters);
  }, [selectedFolder,content,triggerFetch,contacts]);

 

  // Fetch mails for the current page
  const fetchMails = async (pageIndex) => {
    if (!selectedFolder) return;

    const folderID = folders.find(
      (folder) => folder.name === selectedFolder
    )?.id;
    if (!folderID) return;

    try {
      const response = await axios.get(`http://localhost:8080/mail/get/${folderID}`, {
        params: {
          pageNumber: pageIndex,
          pageSize: 5,
          sort: sortBy,
        },
      });

      if (response.status === 200 && response.data) {
        setCurrentMails(response.data);
      }
    } catch (error) {
      console.error("Error fetching mails:", error);
    }
  };

  useEffect(() => {
    setPage(0);
    if (selectedFolder || triggerFetch) {
      fetchMails(0);
      setTriggerFetch(false);
    }
  }, [selectedFolder, sortBy,triggerFetch]);

  useEffect(() => {
    setPage(0);
    setSearching(false)
    setCurrentMails([])
  }, [selectedFolder]);

  const handleSortChange = (event) => {
    setSortBy(event.target.value);
    setPage(0);
    searching?handleMailSearch(0):fetchMails(0);
  };
useEffect(()=>{
  setSearchWord("")
},[content]
)
  const handleNextPage = () => {
    const nextPage = page + 1;
    setPage(nextPage);
    console.log(searching)
    console.log(page)
    searching?handleMailSearch(nextPage):fetchMails(nextPage);
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      const prevPage = page - 1;
      setPage(prevPage);
      searching?handleMailSearch(prevPage):fetchMails(prevPage);
    }
  };

  const handleMailSearch = async (page)=> {
    setSearching(true)
    const criteriaDto = {searchWord : searchWord,criterias:selectedFilters,sortedBy:sortBy||"date",pageNumber:page}
    let folderId = null;
    if(searchFolder) folderId = folders.find((folder) => folder.name === selectedFolder)?.id;
    
    console.log(criteriaDto);
   
      try {
        const response = await axios.post(`http://localhost:8080/mail/search/${userId}`,criteriaDto, {
          params: {
            pageNumber: page,
            pageSize: 5,
            sortedBy: sortBy,
            folderId : folderId
          },
        });
  
        if (response.status === 200 && response.data) {
          console.log(response.data)
          setCurrentMails(response.data);
        }
      } catch (error) {
        console.error("Error searching mails:", error.response.data);
      }


  }

  const handleContactSearch = async ()=> {
    const criteriaDto = {searchWord : searchWord,criterias:selectedFilters}
    console.log(criteriaDto);
   
      try {
        const response = await axios.post(`http://localhost:8080/contact/search/${userId}`,criteriaDto);
  
        if (response.status === 200 && response.data) {
          console.log(response.data)
          setContacts(response.data);
        }
      } catch (error) {
        console.error("Error searching cntacts:", error.response.data);
      }

  }

  const handleMove = async () => {
    if (!selectedMoveFolder || checkedMails.length === 0) {

      if(selectedMoveFolder==="Drafts"){
        setSnackbarSeverity("info");
      setSnackbarMessage("only draft mails can be added to draft !");
      setOpenSnackbar(true);
      }

      setSnackbarSeverity("error");
      setSnackbarMessage("no selected folder or mails");
      setOpenSnackbar(true);
      return;
    }

    const folderID = folders.find((folder) => folder.name === selectedMoveFolder)?.id;
    if (!folderID) {
      console.error("Invalid folder selected.");
      return;
    }

    const moveMails = {
      userId,
      destinationFolderId: folderID,
      mailIds: checkedMails,
    };
    try {
      const response = await axios.put("http://localhost:8080/mail/move", moveMails);

      if (response.status === 200) {
        setTriggerFetch(true);
        setCheckedMails([]);
        setSnackbarSeverity("Sucsss");
        setSnackbarMessage("Move done")
        setOpenSnackbar(true);
      }
    } catch (error) {
      setSnackbarSeverity("error");
      setSnackbarMessage(error.response.data);
      setOpenSnackbar(true);
    }
    setMoveDialogOpen(false); // Close the dialog after moving mails
 
  };

  const handleDelete = () => {
    axios
      .delete("http://localhost:8080/mail/delete", {
        data: { userId, mailIds: checkedMails },
      })
      .then((response) => {
        console.log("Mails deleted:", response.data);
        setTriggerFetch(true);
        setCheckedMails([])
        setSnackbarSeverity("Sucsss");
        setSnackbarMessage("Delete Done");
        setOpenSnackbar(true);
      })
      .catch((error) => {
        console.error("Error deleting mails:", error);
      });
  };

  return (
    
    <Box sx={{ display: "flex" ,justifyContent:"space-between",alignItems:"center"}}>
      <SideBar
      setCheckedMails={setCheckedMails}
      handleMove={handleMove}
        contacts={contacts}
        setTriggerFetch={setTriggerFetch}
        folders={folders}
        selectedFolder={selectedFolder}
        setSelectedFolder={setSelectedFolder}
        setFolders={setFolders}
        setContent={setContent}
        userId={userId}
        handleLogout={handleLogoutApp}
        userName={userName}
      />

      {/* Main Content Area */}
      <Box
        sx={{
          marginLeft: "270px",
          width: "calc(100% - 250px)",
          padding: "20px",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* Top Section: Search Bar and Contacts Button */}
        <Box
  sx={{
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: 2,
  }}
> 
    <div style={{ marginLeft:"25%",display:"flex"}}>
   
    <Filter selectedFilters={selectedFilters} setSelectedFilters={setSelectedFilters} folderId={searchFolder} setFolderId={setSearchFolder} content={content}  /> {/* Filter moved to the right of the SearchBar */}
    <SearchBar query={searchWord} setQuery={setSearchWord} /> {/* SearchBar remains in the center */}
     <IconButton variant="contained" onClick={()=>{content==="contacts"?handleContactSearch():handleMailSearch(0)}} ><SearchIcon></SearchIcon></IconButton> {/* Search button added to the left */} </div>
    
 
  <ContactsButton setContent={setContent} /> {/* Moved to the far left */}
 
</Box>
  {content==="mails" && 
       
       <Box
       sx={{
         marginBottom: "10px",
         display: "flex",
         alignItems: "center",
         justifyContent: "space-between",
         marginBottom: 2,
       }}
     >
      

       {/* Actions */}
       <Box
         style={{
           display: "flex",
           gap: "10px",
         }}
       >
         <Button color="secondary" disabled >{searching?"Search":selectedFolder}</Button>
         {!(selectedFolder==="Drafts")&&(
           <IconButton color="primary" onClick={() => setMoveDialogOpen(true)}>
             <DriveFileMoveIcon />
           </IconButton>

)}
         {!(selectedFolder==="Trash") &&(
           <IconButton color="error" onClick={handleDelete}>
                 <AutoDeleteIcon />
           </IconButton>
         )}
         
         <IconButton onClick={() => searching ? handleMailSearch(0) : fetchMails(0)}>
           <RefreshIcon />
         </IconButton>
         <div>
         <label>Page </label>
         <IconButton onClick={handlePreviousPage} disabled={page === 0}>
           <ArrowBackIosIcon />
         </IconButton>
         <label>{page + 1}</label>
         <IconButton
           onClick={handleNextPage}
           disabled={currentMails.length < 5}
         >
           <ArrowForwardIosIcon />
         </IconButton>
       </div>
       </Box>
       {/* Sort dropdown */}
       <FormControl variant="standard" sx={{ minWidth: 150 }}>
         <InputLabel id="sort-label">Sort by:</InputLabel>
         <Select
           labelId="sort-label"
           id="sort-select"
           value={sortBy}
           onChange={handleSortChange}
         >
         
           <MenuItem value="date">Date</MenuItem>
           <MenuItem value="priority">Importance</MenuItem>
         </Select>
       </FormControl>
     </Box>
  }
      

        {/* Content */}
        <Box sx={{  backgroundColor: "#f4f4f4", padding: 2 }}>
          {content === "mails" && (
            <Mail
            handleMove={handleMove}
            contacts={contacts}
              folders={folders}
              selectedFolder={selectedFolder}
              userId={userId}
              mails={currentMails}
              setTriggerFetch={setTriggerFetch}
              checkedMails={checkedMails}
              setCheckedMails={setCheckedMails}
            />
          )}

          {content === "contacts" && <MyContacts userId={userId} contacts={contacts} setContacts={setContacts}/>}
        </Box>
      </Box>

      {/* Move Dialog */}
      <Dialog open={moveDialogOpen} onClose={() => setMoveDialogOpen(false)}>
        <DialogTitle>Move Mails</DialogTitle>
        <DialogContent>
          <FormControl fullWidth>
            <InputLabel id="move-folder-label">Select Folder</InputLabel>
            <Select
              labelId="move-folder-label"
              value={selectedMoveFolder}
              onChange={(e) => setSelectedMoveFolder(e.target.value)}
            >
              {folders.map((folder) => (

              folder.name!="Trash"&&<MenuItem key={folder.id} value={folder.name}>

                  {folder.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setMoveDialogOpen(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={()=>handleMove()} color="primary" variant="contained">
            Move
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
              open={openSnackbar}
              autoHideDuration={2000}
              onClose={() => setOpenSnackbar(false)}
              anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
            >
              <Alert
                onClose={() => setOpenSnackbar(false)}
                severity={snackbarSeverity}
                variant="filled"
              >
                {snackbarMessage}
              </Alert>
            </Snackbar>
    </Box>
  );
}

export default DivContent;
