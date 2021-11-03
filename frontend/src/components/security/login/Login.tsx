import React, {ChangeEvent, useState } from 'react'
//component imports
import {Divider, TextField, CardHeader, FormGroup, Button, Card } from '@mui/material';

//interface imports

import {ICredentials } from '../../../interfaces/IEmployee';
import { login } from '../../../slicer/authSlice';
import {useAppDispatch, useAppSelector} from '../../../app/hooks';
import {selectCurrentEmployee} from "../../../slicer/employeeSlice";

type Props = {

};

function Login(props: Props){
    const dispatch = useAppDispatch();
    const employee = useAppSelector(selectCurrentEmployee)
    const [credentials, setsCredentials] = useState<ICredentials>({username: employee?.username})

    const handleInput = (e:ChangeEvent<HTMLInputElement>) => {
        setsCredentials({...credentials, password:e.target.value})
    }
    const handleLogin = () => {
        dispatch(login(credentials));
    }
    return(
        <Card>
            <CardHeader title={`Hello ${employee?.firstName} enter your password to login`} align="center"/>
            <Divider/>
            <FormGroup>
                <TextField sx={{my: 1}} required label="Password" type="password" onChange={handleInput}/>
                <Button disabled={!credentials.password} type="submit" onClick={handleLogin}>Register</Button>
            </FormGroup>
        </Card>
    )
}

export default Login;
