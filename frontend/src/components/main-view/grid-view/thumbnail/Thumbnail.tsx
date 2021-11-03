import React from 'react'
import {useHistory} from "react-router";
import {useAppDispatch} from '../../../../app/hooks';
import {chooseCurrentEmployee} from "../../../../slicer/employeeSlice";

//component imports
import {Card, CardHeader, CardMedia} from "@mui/material";

//interface imports
import { IThumbnailData } from '../../../../interfaces/IThumbnailData';

type Props = {
    data: IThumbnailData
}


function Thumbnail({data}: Props) {
    const {title, subtitle, picture, id, alt} = data;
    const history = useHistory();
    const dispatch = useAppDispatch();
    // const onClick = () => {
    //     dispatch(chooseCurrentEmployee(item))
    //     history.push('login')
    // }

    return (
      <Card sx={{height: 500, width: 345}}>
          <CardHeader title={title} subtitle={subtitle} />
          <CardMedia
              component="img"
              height="194"
              image={picture}
              alt={alt}
          />
      </Card>
    )
}


export default Thumbnail;
