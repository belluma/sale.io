import React from 'react'
import {MenuItem, TextField} from "@mui/material";
import {Buttons} from "../../../interfaces/IThumbnailData";
import {ISelectProps, ITextFieldProps} from "../../../interfaces/INewItem";

//component imports

//interface imports

type Props = ISelectProps

function CustomSelect({label, options, model}: Props){
    const handleChange = () => {
    };
    return(
        <TextField
            id={`Select ${label}`}
            select
            label={`Select ${label}`}
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
