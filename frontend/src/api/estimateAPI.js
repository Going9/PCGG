import { apiSpringInstance } from "./index.js";

const api = apiSpringInstance();

async function isCallEstimate(data, success, fail) {
  const params = {
    usage: data[1],
    budget: data[2],
    priority: data[4],
    caseSize: data[6],
    as: data[5],
    ssdSize: data[7],
  };
  await api.get(`/recommends/desktop`, { params }).then(success).catch(fail);
}

export { isCallEstimate };
