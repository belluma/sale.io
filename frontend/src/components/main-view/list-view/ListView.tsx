import React from 'react'

//component imports

import { DataGrid, GridColDef } from '@mui/x-data-grid';
import {IEmployee} from "../../../interfaces/IEmployee";
import {ISupplier} from "../../../interfaces/ISupplier";
import {IProduct} from "../../../interfaces/IProduct";

//interface imports
type Props = {
    rows: IEmployee[] | ISupplier[] | IProduct[],
    columns: GridColDef[]
};


export default function ListView({rows, columns}:Props) {
    return (
        <div style={{ height: 400, width: '100%' }}>
            <DataGrid
                disableColumnMenu
                rows={rows}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5]}
                checkboxSelection
                disableSelectionOnClick
            />
        </div>
    );
}
