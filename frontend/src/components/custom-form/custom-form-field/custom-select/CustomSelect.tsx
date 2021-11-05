import React from 'react'
import {MenuItem, TextField} from "@mui/material";


//component imports


//interface imports

import {IFormFieldProps} from "../../../../interfaces/INewItem";

type Props = IFormFieldProps

function CustomSelect({label, handleChange}: Props){

       const menuItems:string[] = ["abc", "def"]
    return(
        <TextField
            id={label}
            select
            label={label}
            name={label}
            onChange={handleChange}
            helperText="Please select your currency"
        >
            {menuItems.map((option) => (
                <MenuItem key={option} value={option}>
                    {option}
                </MenuItem>
            ))}
        </TextField>
    )
}

export default CustomSelect;
