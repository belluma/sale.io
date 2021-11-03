export interface ICredentials {
    username?: String,
    firstName?: string,
    lastName?: string,
    password?: string,
} 

export interface IEmployee extends ICredentials{
    email?:string,
    phone?: string,
    picture?:string,

}
