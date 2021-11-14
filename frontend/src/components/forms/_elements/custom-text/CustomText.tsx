import React from 'react'
//component imports
import {TextField} from "@mui/material";

//interface imports

import {ITextFieldProps} from "../../../../interfaces/IForms";
import {inputStyles} from "../styles";

type Props = ITextFieldProps;

function CustomText({label, handleChange, model, name}: Props){
    return(
        <TextField
            sx={inputStyles}
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
