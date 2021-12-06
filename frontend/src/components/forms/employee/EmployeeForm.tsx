import Grid from '@material-ui/core/Grid';
import React, {ChangeEvent, useState} from 'react'
import CustomText from "../_elements/custom-text/CustomText";

//component imports

//interface imports

type Props = {};

function EmployeeForm(props: Props){
    const [credentials, setCredentials] = useState(
        {    firstName:'', lastName: '',password:'', email: '',phone:''}
    )
    const onChange = (e:ChangeEvent<HTMLInputElement>) => {
        setCredentials({...credentials, [e.target.name]: e.target.value})
    }
    return(
        <Grid container>
            <Grid item>
                <CustomText name={"firstName"} label={"first name"} value={credentials.firstName} model={"employee"} onChange={onChange}/>
            </Grid>
          <Grid item>
                <CustomText name={"lastName"} label={"last name"} value={credentials.lastName} model={"employee"} onChange={onChange}/>
            </Grid>
          <Grid item>
                <CustomText name={"password"} type="password" label={"password"} value={credentials.password} model={"employee"} onChange={onChange}/>
            </Grid>
          <Grid item>
                <CustomText name={"email"} type='email' label={"email"} value={credentials.email} model={"employee"} onChange={onChange}/>
            </Grid>
        <Grid item>
                <CustomText name={"phone"} label={"phone"} value={credentials.phone} model={"employee"} onChange={onChange}/>
            </Grid>
        </Grid>
    )
}

export default EmployeeForm;
