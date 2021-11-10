import React from 'react'

//component imports
import CustomSelect from "../_elements/custom-select/CustomSelect";

import Contact from "../_elements/contact/Contact";
//interface imports
import {mapWeekdaysToSelectData} from "../helper";

type Props = {};

function Supplier({}: Props){
    const handleChange = () => {
    };
    const props = {handleChange: handleChange, model: "supplier"}
    return(
        <div>
            <Contact {...props}/>
            <CustomSelect name="weekdays" label={"Weekdays"} options={mapWeekdaysToSelectData()} {...props}  />
        </div>
    )
}

export default Supplier;
