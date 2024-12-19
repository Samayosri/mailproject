import { Stack, TextField } from "@mui/material";
import {
  IconButton,

} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import DownloadIcon from "@mui/icons-material/Download";


function MailContent({ mail = {} }) {
 
  const mailDto = {
    id: mail.id || null,
    senderId: mail.senderId || null,
    subject: mail.subject || "",
    senderMailAddress: mail.senderMailAddress || "",
    toReceivers: mail.toReceivers || [],
    ccReceivers: mail.ccReceivers || [],
    bccReceivers: mail.bccReceivers || [],
    body: mail.body || "",
    importance: mail.importance || 3,
    attachments: mail.attachments || [],
    creationDate: mail.creationDate || null,
    folder : mail.folder || "ghost",
    name : mail.name || "ghost"
  };

  const handleViewAttachment = (attachment) => {
    const { file: base64File, name, type } = attachment;
    const byteString = atob(base64File.split(",")[1]);
    const byteNumbers = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
      byteNumbers[i] = byteString.charCodeAt(i);
    }
    const file = new File([byteNumbers], name, { type });
    const fileURL = URL.createObjectURL(file);
    window.open(fileURL, "_blank");
  };

  const handleDownloadAttachment = (attachment) => {
    const link = document.createElement("a");
    link.href = attachment.file;
    link.download = attachment.name;
    link.click();
  };
 
  return (
    <>
      <Stack spacing={2}>
      <TextField
          label="Date"
          value={mailDto.creationDate}
          disabled
          fullWidth
        />
        {/* Sender Field */}
        <TextField
          label="Sender"
          value={mailDto.senderMailAddress}
          disabled
          fullWidth
        />
        {/* Receiver Field */}
        <TextField
          label="Receiver"
          value={mailDto.toReceivers.join(" ")}
          disabled
          fullWidth
        />
       {mailDto.ccReceivers.length!=0 && <TextField
          label="CC"
          value={mailDto.ccReceivers.join(" ")}
          disabled
          fullWidth
        />}
        {/* Subject Field */}
        <TextField
          label="Subject"
          value={mailDto.subject}
          disabled
          fullWidth
        />

        {/* Body Field */}
        <TextField
          label="Body"
          value={mailDto.body}
          disabled
          multiline
          rows={4}
          fullWidth
        />
        {mailDto.attachments.length > 0 && (
          <div style={{ padding: 5 }}>
            <ul style={{ listStyle: "none", padding: 0, margin: 2 }}>
              {mailDto.attachments.map((attachment, index) => (
                <li
                  key={index}
                  style={{ display: "flex", alignItems: "center", marginBottom: 5 }}
                >
                  <span style={{ flex: 1 }}>{attachment.name}</span>
                  <IconButton
                    size="small"
                    onClick={() => handleViewAttachment(attachment)}
                  >
                    <VisibilityIcon sx={{ color: "dodgerblue" }} />
                  </IconButton>
                  <IconButton
                    size="small"
                    onClick={() => handleDownloadAttachment(attachment)}
                  >
                    <DownloadIcon sx={{ color: "dodgerblue" }} />
                  </IconButton>
                </li>
              ))}
            </ul>
          </div>
        )}

       
      </Stack>
    </>
  );
}

export default MailContent;