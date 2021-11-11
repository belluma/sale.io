import React, {ChangeEvent} from 'react'
import {mapWeekdaysToSelectData} from "../helper";

//component imports
import CustomSelect from "../_elements/custom-select/CustomSelect";

import Contact from "../_elements/contact/Contact";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {handleSupplierFormInput, selectSupplierToSave} from "../../../slicer/supplierSlice";
//interface imports


function Supplier(){
    const dispatch = useAppDispatch();
    const supplierToSave = useAppSelector(selectSupplierToSave);
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        dispatch(handleSupplierFormInput({...supplierToSave, [e.target.name]: value}));
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
