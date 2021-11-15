import React, {ChangeEvent, useState} from 'react'
import {useAppDispatch, useAppSelector} from '../../../../../../app/hooks';
import {registerAdmin} from '../../../../../../slicer/authSlice';
import {selectEmployees} from "../../../../../../slicer/employeeSlice";


//component imports
import {Card, CardHeader, Button, Divider, FormGroup, TextField} from '@mui/material';
import {Redirect} from "react-router";

//interface imports
import {IUserCredentials, initialCredentials} from '../../../../../../interfaces/IEmployee';

type Props = {};

function Signup(props: Props) {
    const dispatch = useAppDispatch();
    const employees = useAppSelector(selectEmployees);
    const [credentials, setCredentials] = useState<IUserCredentials>(initialCredentials);
    const [repeatedPassword, setRepeatedPassword] = useState<string>("");
    const [passwordConfirmed, setPasswordConfirmed] = useState<boolean>(false);
    const register = () => {
        credentials.password.length && (credentials.firstName?.length || credentials.lastName?.length)
        && dispatch(registerAdmin(credentials));
    }
    const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
        setCredentials({
            ...credentials,
            [e.target.name]: e.target.value,
        })
        if (e.target.name === "password") confirmPassword(e, false);
    }
    const handleRepeatPassword = (e: ChangeEvent<HTMLInputElement>) => {
        setRepeatedPassword(e.target.value);
        confirmPassword(e, true);
    }
    const confirmPassword = (e: ChangeEvent<HTMLInputElement>, repeat: boolean) => {
        const password = repeat ? credentials.password : repeatedPassword;
        setPasswordConfirmed(password.length > 3 && password === e.target.value);
    }
    return (
        employees.length ? <Redirect to={"/login"}/> : <Card>
            <CardHeader title="Choose your administrator Password" align="center"/>
            <Divider/>
            <FormGroup>
                <TextField onChange={handleInput} sx={{my: 1}} required value={credentials?.firstName} name="firstName"
                           label="firstName" type="text"/>
                <TextField onChange={handleInput} sx={{my: 1}} required value={credentials?.lastName} name="lastName"
                           label="lastName" type="text"/>
                <TextField onChange={handleInput} sx={{my: 1}} required value={credentials?.password} name="password"
                           label="password" type="password"/>
                <TextField onChange={handleRepeatPassword} sx={{my: 1}} required value={repeatedPassword}
                           label="confirm password" type="password"/>
                <Button
                    disabled={!passwordConfirmed || (!credentials.firstName && !credentials.lastName)}
                    type="submit" onClick={register}>Register</Button>
            </FormGroup>
        </Card>
    )
}

export default Signup;
