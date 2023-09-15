<template>
  <div>
    <!-- <v-img
      class="mx-auto my-6"
      max-width="228"
      src="https://cdn.vuetifyjs.com/docs/images/logos/vuetify-logo-v3-slim-text-light.svg"
    ></v-img> -->

    <v-card class="mx-auto pa-4" elevation="8" width="100%" rounded="lg">
      <!-- <div class="text-subtitle-1 text-medium-emphasis">Account</div> -->

      <v-text-field
        density="compact"
        placeholder="Email address"
        prepend-inner-icon="mdi-email-outline"
        variant="outlined"
        v-model="email"
      ></v-text-field>

      <!-- <div
        class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between"
      >
        Password

        <a
          class="text-caption text-decoration-none text-blue"
          href="#"
          rel="noopener noreferrer"
          target="_blank"
        >
          Forgot login password?</a
        >
      </div> -->

      <v-text-field
        :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="visible ? 'text' : 'password'"
        density="compact"
        placeholder="Enter your password"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        @click:append-inner="visible = !visible"
        v-model="password"
      ></v-text-field>

      <!-- <v-card class="mb-12" color="surface-variant" variant="tonal">
        <v-card-text class="text-medium-emphasis text-caption">
          Warning: After 3 consecutive failed login attempts, you account will
          be temporarily locked for three hours. If you must login now, you can
          also click "Forgot login password?" below to reset the login password.
        </v-card-text>
      </v-card> -->

      <v-btn block color="blue" size="large" variant="tonal" @click="login">
        Log In
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
import { useAppStore } from "@/store/app";

const store = useAppStore();
const visible = ref(false);
const email = ref("");
const password = ref("");

const goToSignupPage = () => {
  store.changePageLoginSignup();
};

const login = () => {
  console.log(email.value);
  console.log(password.value);
};
</script>
