import React from 'react'

//component imports

//interface imports

type Props = {
    statusText?: string,
};

function ErrorMessage({statusText}: Props){
    return(
        <div>{statusText}</div>
    )
}

export default ErrorMessage;
