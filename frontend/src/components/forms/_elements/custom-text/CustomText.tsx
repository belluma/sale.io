import React from 'react'
//component imports
import {TextField} from "@mui/material";

//interface imports

import {ITextFieldProps} from "../../../../interfaces/INewItem";

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
