import React, {useState} from 'react'

//component imports
import {MenuItem, TextField} from "@mui/material";

//interface imports
import {ISelectProps} from "../../../../interfaces/INewItem";

type Props = ISelectProps

function CustomSelect({label, options, model, name, handleChange}: Props){
    const [selected, setSelected] = useState('')
    const select = (e: React.ChangeEvent<HTMLInputElement>) =>{
        handleChange(e)
        setSelected(e.target.value)
    }
    return(
        <TextField
            id={`Select ${label}`}
            select
            label={`Select ${label}`}
            name={name}
            value={selected}
            onChange={select}
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
