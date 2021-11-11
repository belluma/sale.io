import React from 'react'
//component imports
import CustomText from "../custom-text/CustomText";

//interface imports

import {IFormProps} from "../../../../interfaces/IForms";

interface IContactProps extends IFormProps {
    model: string
}
type Props = IContactProps;

function Contact({handleChange, model}: Props){
    const props = {handleChange: handleChange, model: model}
    return(
        <section>
        <CustomText name="firstName" label={"first name"}  {...props} />
        <CustomText name="lastName" label={"last name"}  {...props} />
        <CustomText name="phone" label={"phone"}  {...props} />
        <CustomText name="email" label={"email"}  {...props} />
</section>
    )
}

export default Contact;
