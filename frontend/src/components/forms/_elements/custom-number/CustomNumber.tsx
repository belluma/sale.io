import React from 'react'
//component imports
import {InputAdornment, TextField} from "@mui/material";

//interface imports

import {INumberFieldProps} from "../../../../interfaces/IForms";
import {inputStyles} from "../styles";

type Props = INumberFieldProps;

function CustomNumber({ negative, currency,...props}: Props){
    return(
        <TextField
            {...props}
            id={props.label}
            type="number"
            InputProps = {{
                startAdornment: currency && <InputAdornment position="start">€</InputAdornment>,
                inputProps: {min:!negative && 0}}}
            InputLabelProps={{
                shrink: true,
            }}
            sx={inputStyles}
            variant="standard"
        />
    )
}

export default CustomNumber;
