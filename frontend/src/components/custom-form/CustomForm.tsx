import React from 'react'


//component imports

import CustomFormField from "./custom-form-field/CustomFormField";
//interface imports
import {formLabelsAndTypes} from "./formHelper";

type Props = {
    model?: string,
};


function CustomForm({model}: Props) {
    //@ts-ignore
    const {labels, formTypes} = formLabelsAndTypes[model];
    const forms = labels.map((label: string, i: number) => <CustomFormField key={i} label={label}
                                                                            formType={formTypes[i]}/>)
    return (
        <div>{forms}</div>
    )
}

export default CustomForm;
