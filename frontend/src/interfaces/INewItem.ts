import React from "react";
import {Weekdays} from "./weekdays";

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

export type Option = { id: string, name: string }

export interface ISelectProps extends ITextFieldProps {
    options:Option[]
}

