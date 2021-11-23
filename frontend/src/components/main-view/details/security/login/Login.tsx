import React, {ChangeEvent, useState} from 'react'
//component imports
import {Divider, TextField, CardHeader, Button, Card, Grid} from '@mui/material';

//interface imports

import {login} from '../../../../../slicer/authSlice';
import {useAppDispatch, useAppSelector} from '../../../../../app/hooks';
import {selectCurrentEmployeeCredentials} from "../../../../../slicer/employeeSlice";

type Props = {};

function Login(props: Props) {
    const dispatch = useAppDispatch();
    const employee = useAppSelector(selectCurrentEmployeeCredentials)
    const [password, setPassword] = useState<string>("")

    const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
    }
    const handleLogin = () => {
        if (employee && password.length) dispatch(login({...employee, password: password}));
    }
    return (
        <Card sx={{ height: 0.99, display:'flex', flexDirection: 'column'}}>
            <CardHeader title={`Hello ${employee?.firstName} enter your password to login`} align="center"/>
            <Divider/>
            <Grid container flexDirection={"column"} sx={{height: 0.99}} justifyContent={'space-between'}>
                <TextField sx={{my: 1}} required label="Password" type="password" onChange={handleInput}/>
                <Button disabled={!employee || !password.length} color={'primary'}  variant='contained' type="submit" onClick={handleLogin}>Login</Button>
            </Grid>
        </Card>
    )
}

export default Login;
