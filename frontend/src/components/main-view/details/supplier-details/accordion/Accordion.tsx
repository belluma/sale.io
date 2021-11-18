import React from 'react'
import {Accordion, AccordionDetails, AccordionSummary} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

//component imports

//interface imports

type Props = {
    expanded: boolean,
    handleChange: (panel: string) => (e: React.SyntheticEvent) => void,
    summary: string,
    content: JSX.Element,
    panel: string
};

function CustomAccordion({expanded, handleChange, summary, content, panel}: Props) {
    return (
        <Accordion expanded={expanded} onChange={handleChange(panel)}>
            <AccordionSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="panel1bh-content"
                id="panel1bh-header"
            >{summary}</AccordionSummary>
            <AccordionDetails>{content}</AccordionDetails>
        </Accordion>
    )
}

export default CustomAccordion;
