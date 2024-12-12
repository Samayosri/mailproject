import { Stack, TextField } from "@mui/material";

function MailContent({ mail = {} }) {
 
  const defaultEmail = {
    id: mail.id || null,
    senderId: mail.senderId || null,
    subject: mail.subject || "Hello there ",
    senderEmailAddress: mail.senderEmailAddress || "bla bla",
    to: mail.to || "",
    body: mail.body || "",
    importance: mail.importance || null,
    attachments: mail.attachments || [],
    creationDate: mail.creationDate || null, 
  };

 
  return (
    <>
      <Stack spacing={2}>
        {/* Sender Field */}
        <TextField
          label="Sender"
          value={defaultEmail.senderEmailAddress}
          disabled
          fullWidth
        />

        {/* Receiver Field */}
        <TextField
          label="Receiver"
          value={defaultEmail.to}
          disabled
          fullWidth
        />

        {/* Subject Field */}
        <TextField
          label="Subject"
          value={defaultEmail.subject}
          disabled
          fullWidth
        />

        {/* Body Field */}
        <TextField
          label="Body"
          value={defaultEmail.body}
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