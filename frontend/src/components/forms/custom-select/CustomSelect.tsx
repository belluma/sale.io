import React from 'react'

//component imports
import {MenuItem, TextField} from "@mui/material";

//interface imports
import {ISelectProps} from "../../../interfaces/INewItem";

type Props = ISelectProps

function CustomSelect({label, options, model, name, handleChange}: Props){
    return(
        <TextField
            id={`Select ${label}`}
            select
            label={`Select ${label}`}
            name={name}
            onChange={handleChange}
            helperText={`Please select your ${model}`}
        >
            {options.map((option) => (
                <MenuItem key={option} value={option}>
                    {option}
                </MenuItem>
            ))}
        </TextField>
    )
}

export default CustomSelect;
