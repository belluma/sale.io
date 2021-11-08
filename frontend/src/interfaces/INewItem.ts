import React from "react";

export interface IFormProps {
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
}

export interface ITextFieldProps extends IFormProps {
    name: string,
    label: string,
    model: string
}
export interface INumberFieldProps extends ITextFieldProps {
    negative?: boolean,
    currency?: boolean,
}
export interface ISelectProps extends ITextFieldProps{
    options:string[],
}

