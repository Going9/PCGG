import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

async function loadPartListAPI(data, success, fail) {
  const params = {
    q : data.q,
    pages : data.page,
  };
  await apiAuth.get(`/parts/${data.partCategory}`, { params }).then(success).catch(fail);
}

export { loadPartListAPI }
