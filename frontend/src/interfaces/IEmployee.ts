export class CredentialsClass {
    username= "";
    firstName= "";
    lastName= "";
    password= "";
}

export interface ICredentials extends CredentialsClass {
}

export class EmployeeClass extends CredentialsClass {
    email = "";
    phone = "";
    picture = "";
    id = "";
}

export interface IEmployee extends EmployeeClass{
}

export const initialCredentials:ICredentials = {
    firstName:"",
    lastName:"",
    password:"",
    username:""
}
