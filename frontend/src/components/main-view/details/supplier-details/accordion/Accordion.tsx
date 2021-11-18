import React from 'react'


//component imports
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import {Accordion, AccordionDetails, AccordionSummary} from "@mui/material";
import {Panel} from "../SupplierDetails";

//interface imports

type Props = {
    expanded:  Panel,
    handleChange: (panel: Panel) => (e: React.SyntheticEvent) => void,
    summary: string,
    content: JSX.Element,
    panel:  Panel,
};

function CustomAccordion({expanded, handleChange, summary, content, panel}: Props) {
    return (
        <Accordion expanded={expanded === panel} onChange={handleChange(panel)}>
            <AccordionSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls={`${panel}-content`}
                id={`${panel}-header`}
            >{summary}</AccordionSummary>
            <AccordionDetails>{content}</AccordionDetails>
        </Accordion>
    )
}

export default CustomAccordion;
