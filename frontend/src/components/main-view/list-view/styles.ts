import {makeStyles} from "@material-ui/core";


export const useDataGridStyles = makeStyles({
    whiteFont: {
        '& .MuiTablePagination-root': {
            color: 'white'
        },
        '& .MuiButtonBase-root': {
            color: 'white'
        },
        '& .MuiDataGrid-columnHeaderTitle': {
            color: 'white'
        }}
    })
