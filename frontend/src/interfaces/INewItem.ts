import React from "react";

export interface IFormInput {
    name: string,
    value: any,
}
export interface IFormFieldProps {
    label: string,
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}

