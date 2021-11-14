import React from 'react'
//component imports
import {TextField} from "@mui/material";

//interface imports

import {ITextFieldProps} from "../../../../interfaces/IForms";
import {inputStyles} from "../styles";

type Props = ITextFieldProps;

function CustomText({label, model, ...props}: Props){
    return(
        <TextField
            sx={inputStyles}
            id={`${model}-${label}`}
            label={`${model} ${label}`}
            {...props}
            helperText={`type the ${label} of the ${model}`}
            variant="standard"
        />
    )
}

export default CustomText;
