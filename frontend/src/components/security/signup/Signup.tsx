import React, {ChangeEvent, useState} from 'react'
import {useAppDispatch} from '../../../app/hooks';
import { registerAdmin } from '../../../slicer/authSlice';



//component imports
import {Button, Divider, FormGroup, TextField} from '@mui/material';
import {Card, CardHeader} from '@mui/material';

//interface imports
import { ICredentials } from '../../../interfaces/IEmployee';

type Props = {};

function Signup(props: Props) {
    const dispatch = useAppDispatch();
    const [credentials, setCredentials] = useState<ICredentials>();
    const [passwordConfirmed, setPasswordConfirmed] = useState<boolean>(false);
    const [buttonDisabled, setButtonDisabled] = useState<boolean>(true);
    const register = () => {
        credentials && credentials.password && (credentials.firstName || credentials.lastName)
        && dispatch(registerAdmin(credentials));
    }
const handleInput = (e:ChangeEvent<HTMLInputElement>) => {
        setCredentials({...credentials});
    enableButton();
    }
    const confirmPassword = (e:ChangeEvent<HTMLInputElement>) => {
        setPasswordConfirmed(false);
        enableButton();
    }
    const enableButton = () => {
        if(passwordConfirmed && (credentials?.firstName || credentials?.lastName)){
            setButtonDisabled(false);
        }
    }
    return (
        <Card>
            <CardHeader title="Choose your administrator Password" align="center"/>
            <Divider/>
            <FormGroup>
                <TextField onChange={handleInput}  sx={{my: 1}} required label="firstName" type="text"/>
                <TextField onChange={handleInput} sx={{my: 1}} required label="lastName" type="text"/>
                <TextField onChange={handleInput} sx={{my: 1}} required label="password" type="password"/>
                <TextField onChange={confirmPassword} sx={{my: 1}} required label="confirm password" type="password"/>
                <Button disabled={buttonDisabled} type="submit" onClick={register}>Register</Button>
            </FormGroup>
        </Card>
    )
}

export default Signup;
