import { Drawer, Box, Stack, Button, IconButton } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu'; 
import Folder from '../Folder/Folder';
import { useState } from 'react';
import SearchBar from '../SearchBar/SearchBar';
function SideBar() {
   const [isDrawerOpen, setIsDrawerOpen] = useState(false);

   return (
      <Box sx={{ position: 'relative', width: '50%' }}>
         <Box sx={{ textAlign: 'left', mb: 4 }}>
            <IconButton
               size="large"
               edge="start"
             
               aria-label="open drawer"
               sx={{ mr: 4 ,color:"white"}}
               onClick={() => setIsDrawerOpen(true)}
            >
               <MenuIcon />
            </IconButton>
       <SearchBar/>
         </Box>

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
                  width:"90%"
               }}>New mail</Button >
               <Folder name="sent" />
               <Folder name="inbox" />
               <Folder name="trash" />
            </Stack>
         </Drawer>
      </Box>
   );
}

export default SideBar;
