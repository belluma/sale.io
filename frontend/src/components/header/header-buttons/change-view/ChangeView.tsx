import * as React from 'react';
import {useAppDispatch, useAppSelector} from "../../../../app/hooks";
import {selectView, toggleView} from "../../../../slicer/viewSlice";

//components
import { IconButton} from "@mui/material";
import ListViewIcon from "@mui/icons-material/ViewList"
import GridViewIcon from "@mui/icons-material/GridView"
import {useButtonStyles} from "../styles";

export default function ChangeView() {
const dispatch = useAppDispatch()
    const gridView = useAppSelector(selectView);
const handleClick = () => {dispatch(toggleView());}
    const classes = useButtonStyles();
    return (
            <IconButton classes={{root: classes.goldFont}}  type="button" onClick={handleClick}>
                {gridView ? <GridViewIcon /> : <ListViewIcon />}
            </IconButton>
    );
}
