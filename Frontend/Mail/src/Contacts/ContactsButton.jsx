import RecentActorsIcon from '@mui/icons-material/RecentActors';
import { Button } from '@mui/material';
function ContactsButton({setContent}){
    
    function setIsDrawerOpen(){
              setContent("contacts");
    }
    return (
        <>
        
         <Button
          edge="end"
           style={{height:"30px"}}
          aria-label="open drawer"
          sx={{ mr: 4 ,color:"black"}}
          onClick={() => setIsDrawerOpen()}>
         <RecentActorsIcon/>
            Contacts
         </Button>
        </>
       
    );

}
export default ContactsButton;