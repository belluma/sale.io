import React from 'react'
//component imports
import CustomText from "../custom-text/CustomText";

//interface imports

import {IFormProps} from "../../../../interfaces/IForms";

interface IContactProps extends IFormProps {
    model: string,
    firstName?:string,
    lastName?:string,
    phone?:string,
    email?:string,
}
type Props = IContactProps;

function Contact({firstName, lastName, phone, email, ...props}: Props){
    return(
        <section>
        <CustomText name="firstName" label={"first name"} value={firstName}  {...props} />
        <CustomText name="lastName" label={"last name"} value={lastName}  {...props} />
        <CustomText name="phone" label={"phone"} value={phone}  {...props} />
        <CustomText name="email" label={"email"} value={email}  {...props} />
</section>
    )
}

export default Contact;
