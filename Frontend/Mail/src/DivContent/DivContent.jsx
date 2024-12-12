import Mail from "../Mail/Mail";
import SideBar from "../SideBar/SideBar";
import { Box, Button, Stack } from "@mui/material";
function DivContent({content,setContent , selectedFolder,folders ,setSelectedFolder }){
     return(
        <>
      
        <SideBar folders={folders} selectedFolder={selectedFolder} setSelectedFolder={setSelectedFolder }setContent={setContent}></SideBar>
        <div style={{width:"80%",margin:"auto" ,background:"white",height:"100%"}}>
        {
          content==="mails"&& <Mail folders={folders} selectedFolder={selectedFolder} ></Mail>
         
        }
        {
             content==="contacts"&& <Stack>
                   <h1 color="black">samaa</h1>
             </Stack>
        }
        </div>
       
        </>
     );
}
export default DivContent;