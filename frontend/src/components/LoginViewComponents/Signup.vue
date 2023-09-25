<template>
  <div>
    <v-card class="mx-auto pa-4" elevation="8" width="100%" rounded="lg">
      <v-text-field
        density="compact"
        placeholder="Email address"
        prepend-inner-icon="mdi-email-outline"
        variant="outlined"
        v-model="email"
      ></v-text-field>

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

      <v-text-field
        density="compact"
        placeholder="name"
        prepend-inner-icon="mdi-emoticon-outline"
        variant="outlined"
        v-model="name"
      ></v-text-field>

      <v-text-field
        density="compact"
        placeholder="nickname"
        prepend-inner-icon="mdi-star-face"
        variant="outlined"
        v-model="nickname"
      ></v-text-field>

      <v-btn
        block
        color="blue"
        size="large"
        variant="tonal"
        @click="signupEvent"
      >
        sign up
      </v-btn>

      <v-card-text class="text-center">
        <v-btn
          class="text-blue text-decoration-none"
          rel="noopener noreferrer"
          target="_blank"
          @click="goToLoginPage"
        >
          Log in now <v-icon icon="mdi-chevron-right"></v-icon>
        </v-btn>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { userStore } from "@/store/userStore";
import { signupAPI } from "@/api/userAPI";

const store = userStore();
const visible = ref(false);
const email = ref("");
const password = ref("");
const name = ref("");
const nickname = ref("");

const goToLoginPage = () => {
  store.changePageLoginSignup();
};

const signupEvent = () => {
  const userInput = {
    email: email.value,
    password: password.value,
    name: name.value,
    nickname: nickname.value,
  };

  signupAPI(
    userInput,
    ({ data }) => {
      let msg = "회원가입이 완료되었습니다.";
      if (data == null) {
        msg = "회원가입이 실패했습니다.";
      }
      alert(msg);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>
