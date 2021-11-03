import React from 'react'
import {useHistory} from "react-router";
import {useAppDispatch} from '../../../../app/hooks';
import {chooseCurrentEmployee} from "../../../../slicer/employeeSlice";
import {IEmployee} from "../../../../interfaces/IEmployee";

//component imports

//interface imports

type Props = {
    item: IEmployee
};

function Thumbnail({item}: Props) {
    const history = useHistory();
    const dispatch = useAppDispatch();
    const onClick = () => {
        dispatch(chooseCurrentEmployee(item))
        history.push('login')
    }

    return (
        <div onClick={onClick}>{item.username}</div>
    )
}


export default Thumbnail;
