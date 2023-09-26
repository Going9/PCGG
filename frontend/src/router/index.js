// Composables
import { createRouter, createWebHistory } from "vue-router";
import Layout from "@/layouts/default/Default"
import Home from "@/views/HomeView"
import Login from "@/views/LoginView.vue"
import Peripheral from "@/views/PeripheralView.vue"
import RecommendationView from "@/views/RecommendationView.vue"
import Share from "@/views/ShareView.vue"
import CreateShare from "@/views/CreateShareView.vue"
import ShareDetail from "@/views/ShareDetailView.vue"
import Simulation from "@/views/SimulationView.vue"

const routes = [
  {
    path: "/",
    component: Layout,
    children: [
      {
        path: "",
        name: "Home",
        component: Home,
      },
      {
        path: "/login",
        name: "Login",
        component: Login,
      },
      {
        path: "/peripheral",
        name: "Peripheral",
        component: Peripheral
      },
      {
        path: "/recommendation",
        name: "RecommendationView",
        component: RecommendationView,
      },
      {
        path: "",
        children: [
          {
            path: "/share",
            name: "Share",
            component: Share,
          },
          {
            path: "/share/createshare",
            name: "CreateShare",
            component: CreateShare,
          },
          {
            path: "/share/sharedetail",
            name: "ShareDetail",
            component:ShareDetail,
          },
        ],
      },
      {
        path: "/simulation",
        name: "Simulation",
        component: Simulation,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
