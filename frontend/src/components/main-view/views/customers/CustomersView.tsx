import React, {useEffect} from 'react'
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {getAllOpenCustomers, selectCustomers} from "../../../../slicer/customerSlice";
import {parseCustomerToThumbnail} from "../../thumbnail/helper";
import GridView from "../../grid-view/GridView";
import {Views} from "../../../../interfaces/IThumbnail";

//component imports

//interface imports

type Props = {};

function CustomersView(props: Props){
    const dispatch = useAppDispatch();
    useEffect(() => {
        dispatch(getAllOpenCustomers());
    }, [dispatch]);
    const products = useAppSelector(selectCustomers)
    const thumbnails = products.map(parseCustomerToThumbnail);
    return(
        <GridView customer gridItems={thumbnails.slice(9)} view={Views.CUSTOMER}/>

)
}

export default CustomersView;
