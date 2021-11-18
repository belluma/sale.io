import React from 'react'
import {ISupplier} from "../../../../../../interfaces/ISupplier";
import {images} from "../../../../helpers";
import {Card, CardMedia} from "@mui/material";
import {selectCurrentSupplier} from "../../../../../../slicer/supplierSlice";
import {useAppSelector} from "../../../../../../app/hooks";

//component imports

//interface imports

type Props = {
};

function LogoAccordion({}: Props){
    const supplier = useAppSelector(selectCurrentSupplier);
    return(
      <Card>
          <CardMedia component="img" sx={{width: .99}} image={supplier.picture || images.supplier} alt={"company logo"}/>
      </Card>
    )
}

export default LogoAccordion;
