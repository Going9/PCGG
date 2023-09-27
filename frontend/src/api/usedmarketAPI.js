import { apiSpringAuthInstance, apiSpringInstance } from "./index.js";

const apiAuth = apiSpringAuthInstance();
const api = apiSpringInstance();

// 중고거래 게시글 작성
async function usedMarketAPI(data, success, fail) {
    console.log(data);
    await apiAuth
      .post('/used-market', JSON.stringify(data))
      .then(success)
      .catch(fail);
}

// 중고거래 게시글 목록 출력
async function listUsedMarketPost(success, fail) {
  console.log("****");

  await api
    .get('/used-market')
    .then((response) => {
      console.log(response.data);
    })
    .catch(fail);
}

export {
  usedMarketAPI
};        
  
