import React from 'react'
import {MenuItem, TextField} from "@mui/material";
import {Buttons} from "../../../interfaces/IThumbnailData";
import {ISelectProps, ITextFieldProps} from "../../../interfaces/INewItem";

//component imports

//interface imports

type Props = ISelectProps

function CustomSelect({label, options, model, name}: Props){
    const handleChange = () => {
    };
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
