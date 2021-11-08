import React from "react";

export interface ITextFieldProps {

    label: string,
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    model: string
}
export interface ISelectProps extends ITextFieldProps{
    options:string[],
}

