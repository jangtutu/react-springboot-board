import React from 'react';
import './App.css';
import BoardItem from 'components/BoardItem';
import { latestBoardListMock } from 'mocks';

function App() {
  return (
    <div>
      {latestBoardListMock.map(boardListItem => <BoardItem boardListItem={boardListItem}/>)}
    </div>
  );
}

export default App;
