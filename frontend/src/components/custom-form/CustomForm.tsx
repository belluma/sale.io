import React from 'react'


//component imports

import CustomFormField from "./custom-form-field/CustomFormField";
//interface imports
import {formLabelsAndTypes} from "./formHelper";
import {Button} from "@mui/material";

type Props = {
    model?: string,
};


function CustomForm({model}: Props) {
    //@ts-ignore
    const {labels, formTypes} = formLabelsAndTypes[model];
    const forms = labels.map((label: string, i: number) => <CustomFormField key={i} label={label}
                                                                            formType={formTypes[i]}/>)
    const handleClick = (e: React.MouseEvent<HTMLButtonElement>) =>{
        console.log(Object.keys(e.target))
    }
    return (
        <form>{forms}<Button onClick={handleClick}>test</Button></form>
    )
}

export default CustomForm;
