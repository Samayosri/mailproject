import { Stack, TextField } from "@mui/material";

function MailContent({ sender, receiver, subject, body }) {
  return (
    <>
      <Stack spacing={2}>
        {/* Sender Field */}
        <TextField
          label="Sender"
          value={sender}
          disabled
          fullWidth
        />
        
        {/* Receiver Field */}
        <TextField
          label="Receiver"
          value={receiver}
          disabled
          fullWidth
        />

        {/* Subject Field */}
        <TextField
          label="Subject"
          value={subject}
          disabled
          fullWidth
        />

        {/* Body Field */}
        <TextField
          label="Body"
          value={body}
          disabled
          multiline
          rows={4}
          fullWidth
        />
      </Stack>
    </>
  );
}

export default MailContent;
