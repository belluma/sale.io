import React from 'react'
import {images} from "../../../../helpers";
import {selectCurrentSupplier} from "../../../../../../slicer/supplierSlice";
import {useAppSelector} from "../../../../../../app/hooks";

//component imports
import {Card, CardMedia} from "@mui/material";

//interface imports

type Props = {
};

function LogoAccordion(props: Props){
    const supplier = useAppSelector(selectCurrentSupplier);
    return(
      <Card>
          <CardMedia component="img" sx={{width: .99}} image={supplier.picture || images.supplier} alt={"company logo"}/>
      </Card>
    )
}

export default LogoAccordion;
