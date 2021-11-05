import React from 'react'
import {TextField} from "@mui/material";
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {handleFormInput, selectItemToSave} from "../../../../slicer/newItemSlice";

//component imports

//interface imports

type Props = {
    label:string
};

function NumberInput({label}: Props){
    const dispatch = useAppDispatch();
    const itemToSave = useAppSelector(selectItemToSave)
    const handleInput = (e:React.ChangeEvent<HTMLInputElement>) => {
        const updatedValue = {[e.currentTarget.name]: e.target.value}
        dispatch(handleFormInput({...itemToSave, ...updatedValue}))
    }
    return(
        <TextField
            onChange={handleInput}
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
