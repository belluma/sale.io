import React from 'react'
import NumberInput from "./number-input/NumberInput";
import CustomSelect from "./custom-select/CustomSelect";
import TextInput from "./text-input/TextInput";

//component imports

//interface imports

type Props = {
    label: string,
    formType: any,
}


function CustomFormField({label, formType}: Props) {
    console.log(formType.length > 1)

    return (
        formType === 1 ? <NumberInput label={label}/> :
            formType === "" ? <TextInput label={label}/> :
                <CustomSelect label={label}/>
    )
}

export default CustomFormField;
