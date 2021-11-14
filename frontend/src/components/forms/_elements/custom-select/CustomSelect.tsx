import React, {useState} from 'react'

//component imports
import {MenuItem, TextField} from "@mui/material";

//interface imports
import {ISelectProps, Option} from "../../../../interfaces/IForms";
import {inputStyles} from "../styles";

type Props = ISelectProps

function CustomSelect({label, options, model, name, handleChange}: Props){
    const [selected, setSelected] = useState('')
    const select = (e: React.ChangeEvent<HTMLInputElement>) =>{
        handleChange(e)
        setSelected(e.target.value)
    }
    return(
        <TextField
            sx={inputStyles}
            id={`Select ${label}`}
            select
            label={`Select ${label}`}
            name={name}
            value={selected}
            onChange={select}
            helperText={`Please select the ${model}`}
        >
            {options.map(({id, name: option}:Option) => (
                <MenuItem key={id} value={id}>
                    {option}
                </MenuItem>
            ))}
        </TextField>
    )
}

export default CustomSelect;
