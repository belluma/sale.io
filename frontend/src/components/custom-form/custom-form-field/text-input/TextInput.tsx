import React from 'react'
//component imports

import {TextField} from "@mui/material";

//interface imports

type Props = {
    label:string
};

function TextInput(props: Props){
    return(
        <TextField
            id="outlined-required"
            label="Required"
            defaultValue="Hello World"
        />
    )
}

export default TextInput;
