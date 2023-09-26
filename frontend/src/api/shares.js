import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

// 회원가입
async function loadSharedList(data, success, fail) {
  const params = { pages : data.page }
  await api.get("/shares/", JSON.stringify(params)).then(success).catch(fail);
}

async function createSharedPost(data, success, fail) {
const body = { data }
  await api.post("/shares/", JSON.stringify(body)).then(success).catch(fail);
}


export { loadSharedList, createSharedPost}
