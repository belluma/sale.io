import React from 'react'
import {TextField} from "@mui/material";
import {ITextFieldProps} from "../../../interfaces/INewItem";

//component imports

//interface imports

type Props = ITextFieldProps;

function CustomText({label, handleChange, model}: Props){
    return(
        <TextField
            onChange={handleChange}
            id={`${model}-${label}`}
            label={`${model} ${label}`}
            name={label}
            helperText={`type the ${label} of the ${model}`}
            variant="standard"
        />
    )
}

export default CustomText;
