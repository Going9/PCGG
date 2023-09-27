// Composables
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    component: () => import("@/layouts/default/Default.vue"),
    children: [
      {
        path: "",
        name: "Home",
        component: () =>
          import("@/views/HomeView.vue"),
      },
      {
        path: "/login",
        name: "Login",
        component: () => import("@/views/LoginView.vue"),
      },
      {
        path: "/peripheral",
        name: "Peripheral",
        component: () => import("@/views/PeripheralView.vue"),
      },
      {
        path: "/recommendation",
        name: "RecommendationView",
        component: () => import("@/views/RecommendationView.vue"),
      },
      {
        path: "/share",
        children: [
          {
            path: "",
            name: "Share",
            component: () => import("@/views/ShareView.vue"),
          },
          {
            path: "createshare",
            name: "CreateShare",
            component: () => import("@/views/CreateShareView.vue"),
          },
          {
            path: "sharedetail",
            name: "ShareDetail",
            component: () => import("@/views/ShareDetailView.vue"),
          },
        ],
      },
      {
        path: "/simulation",
        name: "Simulation",
        component: () => import("@/views/SimulationView.vue"),
      },
      {
        path: "/used-market",
        children: [
          {
            path: "",
            name: "UsedMarket",
            component: () => import("@/views/UsedMarketView.vue"),
          },
          {
            path: "createusedmarket",
            name: "CreateUsedMarket",
            component: () => import("@/views/CreateUsedMarketView.vue"),
          }

        ],
      }
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
