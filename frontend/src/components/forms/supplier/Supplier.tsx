import React from 'react'

//component imports
import CustomSelect from "../_elements/custom-select/CustomSelect";

import Contact from "../_elements/contact/Contact";
//interface imports
import {IFormProps} from "../../../interfaces/INewItem";
import {Weekdays} from "../../../interfaces/weekdays";

type Props = IFormProps;

function Supplier({handleChange}: Props){
    const props = {handleChange: handleChange, model: "supplier"}
    return(
        <div>
            <Contact {...props}/>
            <CustomSelect name="weekdays" label={"Weekdays"} options={Object.values(Weekdays)} {...props}  />
        </div>
    )
}

export default Supplier;
