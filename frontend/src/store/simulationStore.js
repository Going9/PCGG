import { defineStore } from "pinia";
import { isSimulate } from "../api/simulationAPI";

export const useSimulationStore = defineStore("simulation", {
  state: () => ({
    simulateResult: [],
  }),
  getters: {},
  actions: {
    async goSimulate(value) {
      await isSimulate(
        value,
        ({ data }) => {
          this.simulateResult = [];
          this.simulateResult.push(data[0]);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
});
