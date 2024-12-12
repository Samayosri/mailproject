import { Drawer, Box, Stack, Button, IconButton } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu'; 
import Folder from '../Folder/Folder';
import { useState } from 'react';
import SearchBar from '../SearchBar/SearchBar';
import Contacts from '../Contacts/Contacts';
function SideBar({selectedFolder,setSelectedFolder, setContent={setContent}}) {
   const [isDrawerOpen, setIsDrawerOpen] = useState(false);

   return (
     
      <Box sx={{ position: 'relative', width: '100%' ,display:"flex" } } >
            <div style={{
               display:"flex",
               justifyContent:"space-between",
               alignItems:"center",
               width:"100%"
            }}
            >
            <Box sx={{ textAlign: 'left', mb: 4 ,marginTop:"10px"}}>
            <IconButton
               size="large"
               edge="start"
             
               aria-label="open drawer"
               sx={{ mr: 4 ,color:"black"}}
               onClick={() => setIsDrawerOpen(true)}
            >
               <MenuIcon />
            </IconButton>
            </Box>
        
           <SearchBar>
            </SearchBar>
            <Contacts setContent={setContent}/>
   
            </div>
       
    
            
         {/* Drawer */}
         <Drawer
            variant="temporary"
            open={isDrawerOpen}
            onClose={() => setIsDrawerOpen(false)} 
            PaperProps={{
               sx: {
                  width: '250px', 
                  marginTop: '56px', 
               },
            }}
         >
            <Stack
               spacing={2}
               sx={{
                  justifyContent: 'center',
                  alignItems: 'center',
                  padding: 2,
               }}
            >
               <Button variant="contained" style={{
                  width:"90%",backgroundColor:" #b89696"
               }}>New mail</Button >
               <Folder name="sent" setContent={setContent} selectedFolder={selectedFolder} setSelectedFolder={setSelectedFolder}/>
               <Folder name="inbox"  setContent={setContent} selectedFolder={selectedFolder} setSelectedFolder={setSelectedFolder}/>
               <Folder name="trash"  setContent={setContent} selectedFolder={selectedFolder} setSelectedFolder={setSelectedFolder}/>
            </Stack>
         </Drawer>
      </Box>
   );
}

export default SideBar;
