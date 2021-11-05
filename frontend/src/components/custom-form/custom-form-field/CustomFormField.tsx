import React from 'react'
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {handleFormInput, selectItemToSave} from "../../../slicer/newItemSlice";


//component imports

import NumberInput from "./number-input/NumberInput";
import CustomSelect from "./custom-select/CustomSelect";
import TextInput from "./text-input/TextInput";

//interface imports

type Props = {
    label: string,
    formType: any,
}


function CustomFormField({label, formType}: Props) {
    const dispatch = useAppDispatch();
    const itemToSave = useAppSelector(selectItemToSave)
    const handleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        const updatedValue = {[e.target.name]: e.target.value}
        dispatch(handleFormInput({...itemToSave, ...updatedValue}))
    }
    if (formType === 1) return <NumberInput label={label} handleChange={handleInput}/>
    if (formType === "") return <TextInput label={label} handleChange={handleInput}/>
    return <CustomSelect label={label} handleChange={handleInput}/>

}

export default CustomFormField;
