import React, {ChangeEvent, useState } from 'react'
//component imports
import {Divider, TextField, CardHeader, FormGroup, Button, Card } from '@mui/material';

//interface imports

import {ICredentials, IEmployee } from '../../../interfaces/IEmployee';
import { login } from '../../../slicer/authSlice';
import { useAppDispatch } from '../../../app/hooks';

type Props = {
    employee:IEmployee
};

function Login({employee}: Props){
    const dispatch = useAppDispatch();
    const {firstName, lastName} = employee;
    const [credentials, setsCredentials] = useState<ICredentials>({firstName, lastName})

    const handleInput = (e:ChangeEvent<HTMLInputElement>) => {
        setsCredentials({...credentials, password:e.target.value})
    }
    const handleLogin = () => {
        dispatch(login(credentials));
    }
    return(
        <Card>
            <CardHeader title="Choose your administrator Password" align="center"/>
            <Divider/>
            <FormGroup>
                <TextField sx={{my: 1}} required label="Password" type="password" onChange={handleInput}/>
                <Button disabled={!credentials.password} type="submit" onClick={handleLogin}>Register</Button>
            </FormGroup>
        </Card>
    )
}

export default Login;
