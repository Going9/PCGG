import { apiSpringInstance } from "./index.js";

const api = apiSpringInstance();

async function isCallPart(data, success, fail) {
  const params = {
    usage: data[0],
    budget: data[1],
    priority: data[2],
    as: data[3],
  };
  await api.get(`/recommends/desktop`, { params }).then(success).catch(fail);
}

export { isCallPart };
