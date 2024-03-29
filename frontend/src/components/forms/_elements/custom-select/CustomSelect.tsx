import React, {useEffect, useState} from 'react'

//component imports
import {MenuItem, TextField} from "@mui/material";

//interface imports
import {ISelectProps, Option} from "../../../../interfaces/IForms";
import {inputStyles} from "../styles";

type Props = ISelectProps

function CustomSelect({label, options, model, onChange, value, ...props}: Props) {
    const [selected, setSelected] = useState("");
    useEffect(() => {
        if (value) setSelected(value)
    }, [value])
    const select = (e: React.ChangeEvent<HTMLInputElement>) => {
        onChange(e);
        setSelected(e.target.value);
    };
    return (
        <TextField
            sx={inputStyles}
            id={`Select ${label}`}
            select
            label={`Select ${label}`}
            value={selected}
            onChange={select}
            helperText={`Please select the ${model}`}
            {...props}
        >
            {options.map(({id, name: option}: Option) => (
                <MenuItem key={id} value={id}>
                    {option}
                </MenuItem>
            ))}
        </TextField>
    )
}

export default CustomSelect;
