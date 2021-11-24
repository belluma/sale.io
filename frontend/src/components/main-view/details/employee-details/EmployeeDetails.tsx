import React from 'react'
import {selectCurrentEmployee} from "../../../../slicer/employeeSlice";
import {useAppSelector} from "../../../../app/hooks";

import {parseName} from "../../thumbnail/helper";
//component imports
import {Divider, Grid, Typography} from '@mui/material';

//interface imports

type Props = {};

function EmployeeDetails(props: Props){
    const employee = useAppSelector(selectCurrentEmployee)
    const { phone, email} = employee;
    return(
        <Grid container justifyContent="space-between" alignItems="stretch" direction="column" sx={{height:.8}}>
           <Grid item>
            <Typography align="center" variant="h5">
                {parseName(employee)}
            </Typography>
               <Divider variant="fullWidth"/>
           </Grid>
            <Grid item>
                <Typography variant="h5">
                   Contact data
                </Typography>
                <Divider/>
            </Grid>
            <Grid item>
                <Typography variant="h6">
                    phone: {phone}
                </Typography>
            </Grid>
        <Grid item>
                <Typography variant="h6">
                    email: {email}
                </Typography>
            </Grid>
        </Grid>
        )
}

export default EmployeeDetails;
