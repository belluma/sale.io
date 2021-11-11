import React from 'react'
//component imports
import {InputAdornment, TextField} from "@mui/material";

//interface imports

import {INumberFieldProps} from "../../../../interfaces/IForms";

type Props = INumberFieldProps;

function CustomNumber({label, handleChange, name, negative, currency, value}: Props){
    return(
        <TextField
            onChange={handleChange}
            id={label}
            label={label}
            name={name}
            type="number"
            value={value}
            InputProps = {{
                startAdornment: currency && <InputAdornment position="start">€</InputAdornment>,
                inputProps: {min:!negative && 0}}}
            InputLabelProps={{
                shrink: true,
            }}
            variant="standard"
        />
    )
}

export default CustomNumber;
