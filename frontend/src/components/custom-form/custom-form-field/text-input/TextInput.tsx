import React from 'react'
//component imports

import {TextField} from "@mui/material";
import {IFormFieldProps} from "../../../../interfaces/INewItem";

//interface imports

type Props = IFormFieldProps

function TextInput({label, handleChange}: Props){
    return(
        <TextField
            id={label}
            label={label}
            name={label}
            variant="standard"
            onChange={handleChange}
        />
    )
}

export default TextInput;
