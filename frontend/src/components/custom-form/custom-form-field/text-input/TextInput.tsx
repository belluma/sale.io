import React from 'react'
//component imports

import {TextField} from "@mui/material";

//interface imports

type Props = {
    label:string
};

function TextInput({label}: Props){
    return(
        <TextField
            id={label}
            label={label}
            variant="standard"
        />
    )
}

export default TextInput;
