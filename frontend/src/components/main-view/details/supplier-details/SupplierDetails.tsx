import React from 'react'
import {selectCurrentSupplier} from "../../../../slicer/supplierSlice";
import {useAppSelector} from "../../../../app/hooks";
import {selectOrders} from "../../../../slicer/orderSlice";
import {selectProducts} from "../../../../slicer/productSlice";
//component imports

import { Container} from "@mui/material";
import Accordion from "./accordion/CustomAccordion";
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
    const [expanded, setExpanded] = React.useState<Panel>('picture')
    const handleChange = (panel: Panel) => () => {
        setExpanded(panel)
    }


    return (
        <Container>
            <Accordion expanded={expanded} handleChange={handleChange} summary={"Company Logo"}
                       content={<LogoAccordion/>}
                       panel="picture"/>
            <Accordion expanded={expanded} handleChange={handleChange} summary={"Products"}
                       content={<ListAccordion items={supplierProducts}/>}
                       panel="products"/>
            <Accordion expanded={expanded} handleChange={handleChange} summary={"Orders"}
                       content={<ListAccordion items={supplierOrders}/>} panel="orders"/>


        </Container>
    )
}

export default SupplierDetails;
