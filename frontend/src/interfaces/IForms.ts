import React from "react";

export interface IFormProps {
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
}
interface IFormFieldProps extends IFormProps{
    name: string,
    label: string,
    model: string,
}

export interface ITextFieldProps extends IFormFieldProps {
    value?:string
}

export interface INumberFieldProps extends IFormFieldProps {
    negative?: boolean,
    currency?: boolean,
    value?:number,

}

export type Option = { id: string, name: string }

export interface ISelectProps extends ITextFieldProps {
    options:Option[]
}

