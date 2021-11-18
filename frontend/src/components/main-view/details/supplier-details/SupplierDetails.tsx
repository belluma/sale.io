import React from 'react'
import {selectCurrentSupplier} from "../../../../slicer/supplierSlice";
import {useAppSelector} from "../../../../app/hooks";
import {selectOrders} from "../../../../slicer/orderSlice";
import {selectProducts} from "../../../../slicer/productSlice";
import {Accordion, AccordionDetails, AccordionSummary, Container} from "@mui/material";

//component imports
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
//interface imports

type Props = {};

function SupplierDetails(props: Props) {
    const supplier = useAppSelector(selectCurrentSupplier);
    const orders = useAppSelector(selectOrders);
    const products = useAppSelector(selectProducts);
    const supplierOrders = orders.filter(order => order.supplier?.id?.toString() === supplier.id)
    const supplierProducts = products.filter(product => product.suppliers?.some(sup => sup.id?.toString() === supplier.id));

    const [expanded, setExpanded] = React.useState<string>('picture')
    const handleChange = (panel: string) => (event: React.SyntheticEvent, isExpanded: boolean) => {
        setExpanded(panel)
    }

    return (
        <Container>
            <Accordion expanded={expanded === 'picture'} onChange={handleChange("picture")}>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon/>}
                    aria-controls="panel1bh-content"
                    id="panel1bh-header"
                >text</AccordionSummary>
                <AccordionDetails>moretesxt</AccordionDetails>
            </Accordion>
        <Accordion expanded={expanded === 'products'} onChange={handleChange("products")}>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon/>}
                    aria-controls="products"
                    id="supplier-products"
                >text</AccordionSummary>
                <AccordionDetails>moretesxt</AccordionDetails>
            </Accordion>
        </Container>
    )
}

export default SupplierDetails;
