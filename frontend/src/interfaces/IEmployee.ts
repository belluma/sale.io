export interface ICredentials {
    firstName?: string,
    lastName?: string,
    password?: string,
} 

export interface IEmployee extends ICredentials{
    email?:string,
    phone?: string,
    picture?:string,

}
