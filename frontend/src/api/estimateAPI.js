import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

async function isCallEstimatePc(data, success, fail) {
  const params = {
    usage: data[1],
    budget: data[2],
    priority: data[4],
    caseSize: data[6],
    as: data[5],
    ssdSize: data[7],
  };
  await api.post(`/recommends/desktop`, params).then(success).catch(fail);
}

async function isCallEstimateLaptop(data, success, fail) {
  const params = {
    usage: data[1],
    budget: data[2],
    os: data[3],
    priority: data[4],
    as: data[5],
  };
  await api.post(`/recommends/laptop`, params).then(success).catch(fail);
}

export { isCallEstimatePc, isCallEstimateLaptop };
