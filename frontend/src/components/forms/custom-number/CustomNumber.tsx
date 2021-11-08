import React from 'react'
import {TextField} from "@mui/material";
import {ITextFieldProps} from "../../../interfaces/INewItem";

//component imports

//interface imports

type Props = ITextFieldProps;

function CustomNumber({label, handleChange, name}: Props){
    return(
        <TextField
            onChange={handleChange}
            id={label}
            label={label}
            name={name}
            type="number"
            InputLabelProps={{
                shrink: true,
            }}
            variant="standard"
        />
    )
}

export default CustomNumber;
