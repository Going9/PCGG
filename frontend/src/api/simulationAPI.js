import { apiSpringAuthInstance } from "./index.js";

const apiAuth = apiSpringAuthInstance();

async function isSimulate(data, success, fail) {
  const params = {
    cpuId: data[0]["id"],
    coolerId: data[1]["id"],
    mainboardId: data[2]["id"],
    ramId: data[3]["id"],
    gpuId: data[4]["id"],
    ssdId: data[5]["id"],
    caseId: data[6]["id"],
    powerId: data[7]["id"],
  };
  console.log(data);
  console.log(params);
  await apiAuth.post(`/simulations`, params).then(success).catch(fail);
}

export { isSimulate };
