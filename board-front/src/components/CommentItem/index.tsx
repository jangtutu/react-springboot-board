import React from 'react'
import './style.css';
import defaultProfileImage from 'assets/image/default-profile-image.png';
import { CommnetListItem } from 'types/interface';

interface Props {
    commentListItem: CommnetListItem;
}

// component: Comment List Item 컴포넌트 //
export default function commentItem({commentListItem} : Props) {

// properties //
const { nickname, profileImage, writeDatetime, content } = commentListItem;

// render: Comment List Item 랜더링 //
  return (
    <div className='comment-list-item'>
        <div className='comment-list-item-top'>
            <div className='comment-list-item-profile-box'>
                <div className='comment-list-item-profile-image' style={{backgroundImage:`url(${profileImage ? profileImage : defaultProfileImage})`}}></div>
            </div>
            <div className='comment-list-item-nickname'>{nickname}</div>
            <div className='comment-list-item-divider'>{'\|'}</div>
            <div className='comment-list-item-time'>{writeDatetime}</div>
        </div>
        <div className='comment-list-item-main'>
            <div className='comment-list-item-content '>{content}</div>
        </div>
    </div>
  )
}
