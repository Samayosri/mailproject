import * as React from 'react';
import { styled } from '@mui/material/styles';
import { AttachFile } from '@mui/icons-material';
import {
  IconButton,
} from '@mui/material';

const VisuallyHiddenInput = styled('input')({
  clip: 'rect(0 0 0 0)',
  clipPath: 'inset(50%)',
  height: 1,
  overflow: 'hidden',
  position: 'absolute',
  bottom: 0,
  left: 0,
  whiteSpace: 'nowrap',
  width: 1,
});

export default function InputFileUpload({onChange}) {
  return (
    <IconButton
      component="label"
      sx={{ color: 'dodgerblue' }}
    >
      <AttachFile />
      <input
        type="file"
        onChange={onChange}
        multiple
        hidden
      />
    </IconButton>
  );
}
