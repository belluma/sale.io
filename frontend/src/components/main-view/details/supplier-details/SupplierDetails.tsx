import React from 'react'
import {selectCurrentSupplier} from "../../../../slicer/supplierSlice";
import {useAppSelector} from "../../../../app/hooks";
import {selectOrders} from "../../../../slicer/orderSlice";
import {selectProducts} from "../../../../slicer/productSlice";
//component imports

import {Button, Container} from "@mui/material";
import Accordion from "./accordion/Accordion";
import LogoAccordion from "./accordion/logo-accordion/LogoAccordion";
import ListAccordion from "./accordion/list-accordion/ListAccordion";
//interface imports

type Props = {};
export type Panel = "picture" | "products" | "orders"

function SupplierDetails(props: Props) {
    const supplier = useAppSelector(selectCurrentSupplier);
    const orders = useAppSelector(selectOrders);
    const products = useAppSelector(selectProducts);
    const supplierOrders = orders.filter(order => order.supplier?.id === supplier.id)
    const supplierProducts = products.filter(product => product.suppliers?.some(sup => sup.id === supplier.id));
    console.log(products, supplierOrders, supplierProducts, supplier.id);
    const [expanded, setExpanded] = React.useState<Panel>('picture')
    const handleChange = (panel: Panel) => (event: React.SyntheticEvent) => {
        setExpanded(panel)
    }


    return (
        <Container>
            <Accordion expanded={expanded} handleChange={handleChange} summary={"Company Logo"}
                       content={<LogoAccordion/>}
                       panel="picture"/>
            <Accordion expanded={expanded} handleChange={handleChange} summary={"Company Logo"}
                       content={<ListAccordion items={supplierOrders}/>}
                       panel="products"/>
            <Accordion expanded={expanded} handleChange={handleChange} summary={"Company Logo"}
                       content={<ListAccordion items={supplierProducts}/>} panel="orders"/>


        </Container>
    )
}

export default SupplierDetails;
