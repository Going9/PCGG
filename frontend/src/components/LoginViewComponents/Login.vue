<template>
  <div>
    <!-- <v-img
      class="mx-auto my-6"
      max-width="228"
      src="https://cdn.vuetifyjs.com/docs/images/logos/vuetify-logo-v3-slim-text-light.svg"
    ></v-img> -->

    <v-card class="mx-auto pa-4" elevation="8" width="100%" rounded="lg">
      <div class="text-subtitle-1 text-medium-emphasis">Account</div>

      <v-text-field
        density="compact"
        placeholder="Email address"
        prepend-inner-icon="mdi-email-outline"
        variant="outlined"
        v-model="email"
        @keyup.enter="loginEvent"
      ></v-text-field>

      <div
        class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between"
      >
        Password

        <!-- <a
          class="text-caption text-decoration-none text-blue"
          href="#"
          rel="noopener noreferrer"
          target="_blank"
        >
          Forgot login password?</a
        > -->
      </div>

      <v-text-field
        :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="visible ? 'text' : 'password'"
        density="compact"
        placeholder="Enter your password"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        @click:append-inner="visible = !visible"
        v-model="password"
        @keyup.enter="loginEvent"
      ></v-text-field>

      <!-- <v-card class="mb-12" color="surface-variant" variant="tonal">
        <v-card-text class="text-medium-emphasis text-caption">
          Warning: After 3 consecutive failed login attempts, you account will
          be temporarily locked for three hours. If you must login now, you can
          also click "Forgot login password?" below to reset the login password.
        </v-card-text>
      </v-card> -->

      <v-btn
        block
        color="blue"
        size="large"
        variant="tonal"
        @click="loginEvent"
      >
        log in
      </v-btn>

      <v-card-text class="text-center">
        <v-btn
          class="text-blue text-decoration-none"
          rel="noopener noreferrer"
          target="_blank"
          @click="goToSignupPage"
        >
          Sign up now <v-icon icon="mdi-chevron-right"></v-icon>
        </v-btn>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { userStore } from "@/store/userStore";
// import router from "@/router";

const store = userStore();
const visible = ref(false);
const email = ref("");
const password = ref("");

const goToSignupPage = () => {
  store.changePageLoginSignup();
};

const loginEvent = async () => {
  const loginInput = {
    email: email.value,
    password: password.value,
  };

  await store.login(loginInput);
  await store.loginUser();

  // 라우팅을 여기서 삭제하고 userStore로 옮겨서 해결
  // 여기서 하면 왜 안되는지 모르겠음 // async, await 문제는 아닌듯?
  // if (store.isLogin == true) {
  //   router.push({ name: "Home" });
  // }
};
</script>
