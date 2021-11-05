import React from "react";

export interface IFormFieldProps {
    label: string,
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}

