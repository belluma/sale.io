import React, {useState} from 'react'
import {MenuItem, TextField} from "@mui/material";

//component imports

//interface imports

type Props = {
    label:string
};

function CustomSelect({label}: Props){
    const [state, setState] = useState();
    const handleChange =(e:React.ChangeEvent<HTMLInputElement>) => {

    }
    const menuItems:string[] = []
    return(
        <TextField
            id={label}
            select
            label={label}
            value={state}
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
