import React from 'react'
import {mapWeekdaysToSelectData} from "../helper";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";
import {handleSupplierFormInput, selectSupplierToSave} from "../../../slicer/supplierSlice";
//component imports
import CustomSelect from "../_elements/custom-select/CustomSelect";
import Contact from "../_elements/contact/Contact";
//interface imports

function Supplier(){
    const dispatch = useAppDispatch();
    const supplierToSave = useAppSelector(selectSupplierToSave);
    const {firstName, lastName, email, phone, orderDay} = supplierToSave;
    const contactProps = {firstName, lastName, email, phone, orderDay};
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        dispatch(handleSupplierFormInput({...supplierToSave, [e.target.name]: value}));
    };
    const selectWeekday = (e:React.ChangeEvent<HTMLInputElement>) => {
        dispatch(handleSupplierFormInput({...supplierToSave, orderDay: e.target.value}))
    }
    const formProps = { onChange: handleChange, model: "supplier"}
    return(
        <div>
            <Contact  {...contactProps} {...formProps}/>
            <CustomSelect name="weekdays" label={"Weekdays"} options={mapWeekdaysToSelectData()} value={orderDay} onChange={selectWeekday} model="supplier"  />
        </div>
    )
}

export default Supplier;
