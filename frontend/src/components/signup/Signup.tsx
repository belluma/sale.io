import React, { useState } from 'react'
import { useAppDispatch } from '../../app/hooks';
import { registerAdmin } from '../../slicer/authSlice';



//component imports
import {Button, Divider, FormGroup, TextField} from '@mui/material';

import {Card, CardHeader} from '@mui/material';

//interface imports
import { ICredentials } from '../../interfaces/IEmployee';

type Props = {};

function Signup(props: Props) {
    const dispatch = useAppDispatch();
    const [credentials, setCredentials] = useState<ICredentials>();
    const register = () => {
        credentials && credentials.password && (credentials.firstName || credentials.lastName)
        && dispatch(registerAdmin(credentials));

    }


    return (
        <Card>
            <CardHeader title="Choose your administrator Password" align="center"/>
            <Divider/>
            <FormGroup>
                <TextField sx={{my: 1}} required label="firstName" type="text"/>
                <TextField sx={{my: 1}} required label="lastName" type="text"/>
                <TextField sx={{my: 1}} required label="Password" type="password"/>
                <TextField sx={{my: 1}} required label="confirm password" type="password"/>
                <Button type="submit" onClick={register}>Register</Button>
            </FormGroup>
        </Card>
    )
}

export default Signup;
