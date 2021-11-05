import React from 'react'
import {TextField} from "@mui/material";
import {IFormFieldProps} from "../../../../interfaces/INewItem";

//component imports

//interface imports

type Props = IFormFieldProps

function NumberInput({label, handleChange}: Props){

    return(
        <TextField
            onChange={handleChange}
            id={label}
            label={label}
            name={label}
            type="number"
            InputLabelProps={{
                shrink: true,
            }}
            variant="standard"
        />
    )
}

export default NumberInput;
