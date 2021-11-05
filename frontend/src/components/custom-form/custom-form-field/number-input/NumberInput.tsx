import React from 'react'
import {TextField} from "@mui/material";

//component imports

//interface imports

type Props = {
    label:string
};

function NumberInput({label}: Props){
    return(
        <TextField
            id={label}
            label={label}
            type="number"
            InputLabelProps={{
                shrink: true,
            }}
            variant="standard"
        />
    )
}

export default NumberInput;
