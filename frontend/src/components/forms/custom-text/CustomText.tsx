import React from 'react'
import {TextField} from "@mui/material";
import {ITextFieldProps} from "../../../interfaces/INewItem";

//component imports

//interface imports

type Props = ITextFieldProps;

function CustomText({label, handleChange, model, name}: Props){
    return(
        <TextField
            onChange={handleChange}
            id={`${model}-${label}`}
            label={`${model} ${label}`}
            name={name}
            helperText={`type the ${label} of the ${model}`}
            variant="standard"
        />
    )
}

export default CustomText;
