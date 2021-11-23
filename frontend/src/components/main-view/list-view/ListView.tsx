import React from 'react'

//component imports

import {DataGrid, GridColDef} from '@mui/x-data-grid';
import {IEmployee} from "../../../interfaces/IEmployee";
import {ISupplier} from "../../../interfaces/ISupplier";
import {IProduct} from "../../../interfaces/IProduct";
import {Container, Pagination} from "@mui/material";
import {useStyles} from "./styles";

//interface imports
type Props = {
    rows: IEmployee[] | ISupplier[] | IProduct[],
    columns: GridColDef[]
};


export default function ListView({rows, columns}: Props) {
    //@ts-ignore
    const classes = useStyles();
    return (
        <Container maxWidth={false} sx={{height: 0.99}} fixed>
            <DataGrid
                classes={{root: classes.whiteFont, cell: "has-text-white"}}
                autoHeight
                autoPageSize
                disableColumnMenu
                rows={rows}
                columns={columns}
                pageSize={25}
                rowsPerPageOptions={[25]}
                checkboxSelection
                disableSelectionOnClick
            />
        </Container>
    );
}

