export interface ICredentials {
    username?: string,
    firstName: string,
    lastName: string,
    password: string,
} 

export interface IEmployee extends ICredentials{
    email?:string,
    phone?: string,
    picture?:string,
    id?: string
}

export const initialCredentials:ICredentials = {
    firstName:"",
    lastName:"",
    password:"",
    username:""
}
