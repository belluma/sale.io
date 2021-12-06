import Grid from '@material-ui/core/Grid';
import React, {ChangeEvent} from 'react'
import CustomText from "../_elements/custom-text/CustomText";
import {handleEmployeeFormInput, selectEmployeeToSave} from "../../../slicer/employeeSlice";
import {useAppDispatch, useAppSelector} from "../../../app/hooks";

//component imports

//interface imports

type Props = {};

function EmployeeForm(props: Props){
    const dispatch = useAppDispatch();
    const employee = useAppSelector(selectEmployeeToSave);
    const onChange = (e:ChangeEvent<HTMLInputElement>) => {
        dispatch(handleEmployeeFormInput({...employee, [e.target.name]: e.target.value}));
    }
    return(
        <Grid container>
            <Grid item>
                <CustomText name={"firstName"} label={"first name"} value={employee.firstName} model={"employee"} onChange={onChange}/>
            </Grid>
          <Grid item>
                <CustomText name={"lastName"} label={"last name"} value={employee.lastName} model={"employee"} onChange={onChange}/>
            </Grid>
          <Grid item>
                <CustomText name={"password"} type="password" label={"password"} value={employee.password} model={"employee"} onChange={onChange}/>
            </Grid>
          <Grid item>
                <CustomText name={"email"} type='email' label={"email"} value={employee.email} model={"employee"} onChange={onChange}/>
            </Grid>
        <Grid item>
                <CustomText name={"phone"} label={"phone"} value={employee.phone} model={"employee"} onChange={onChange}/>
            </Grid>
        </Grid>
    )
}

export default EmployeeForm;
