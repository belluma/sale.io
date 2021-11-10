import {GridColDef} from "@mui/x-data-grid";

export const productColumns:GridColDef[] =[
    {field:'name', headerName: 'Product Name', flex: 0.125},
    {field:'category', headerName: 'Category', flex: 0.125},
    {field:'suppliers', headerName: 'Suppliers', flex: 0.125},
    {field:'stockCodeSupplier', headerName: 'Stock Code', flex: 0.125},
    {field:'purchasePrice', headerName: 'Purchase Price', flex: 0.1},
    {field:'retailPrice', headerName: 'Retail Price', flex: 0.1},
    {field:'minAmount', headerName: 'Minimum amount to hold in stock', flex: 0.1},
    {field:'maxAmount', headerName: 'maximum amount to hold in stock', flex: 0.1},
    {field:'unitSize', headerName: 'Unit Size', flex: 0.1},
]

export const EmployeeColumns: GridColDef[] = [
{field:"firstName", headerName: "First Name", flex:0.1},
{field:"lastName", headerName: "LastName", flex:0.1},
{field:"email", headerName: "Email", flex:0.1},
{field:"phone", headerName: "Phone", flex:0.1},
]

export const SupplierColumns: GridColDef[] = [
{field:"firstName", headerName: "First Name", flex:0.1},
{field:"lastName", headerName: "LastName", flex:0.1},
{field:"email", headerName: "Email", flex:0.1},
{field:"phone", headerName: "Phone", flex:0.1},
{field:"products", headerName: "Products", flex:0.1},
{field:"orderDay", headerName: "Order Day", flex:0.1},
]

