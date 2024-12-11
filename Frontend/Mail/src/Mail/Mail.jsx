import { Box, Button, Stack } from "@mui/material";
import { useState } from "react";
import MailContent from "./MailContent";

function Mail({ listOfmails }) {

  const [selectedMail, setSelectedMail] = useState(null);


  function handleDisplayMail(m) {
    setSelectedMail(m);
  }

  const mail = listOfmails.map((m, index) => (
    <Button
      key={index}
      variant="contained"
      sx={{ marginBottom: 1 }}
      onClick={() => handleDisplayMail(m)} 
    >
      {m.sender} - {m.subject}
    </Button>
  ));

  return (
    <>
      <Box>
        <Stack spacing={2}>
          {mail} 
        </Stack>

      
        {selectedMail && (
          <Box sx={{ marginTop: 2 }}>
            <MailContent
              sender={selectedMail.sender}
              receiver={selectedMail.receiver}
              subject={selectedMail.subject}
              body={selectedMail.body}
            />
          </Box>
        )}
      </Box>
    </>
  );
}

export default Mail;
