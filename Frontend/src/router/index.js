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
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "home" */ "@/views/HomeView.vue"),
      },
      {
        path: "/peripheral",
        name: "Peripheral",
        component: () => import("@/views/PeripheralViews.vue"),
      },
      {
        path: "/peripheraldetail",
        name: "PeripheralDetail",
        component: () => import("@/views/PeripheralDetail.vue"),
      },
      {
        path: '/recommendation',
        name: 'RecommendationView',
        component: () => import('@/views/RecommendationView.vue'),
      },
      {
        path: '/share',
        name: 'Share',
        component: () => import('@/views/ShareView.vue'),
      },
      {
        path: '/share/createsharepost',
        name: 'CreateSharePost',
        component: () => import('@/views/CreateSharePostView.vue'),
      },
      {
        path: '/share/sharedetail',
        name: 'ShareDetail',
        component: () => import('@/views/ShareDetailView.vue'),
      },



    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
