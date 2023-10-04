<template>
  <div>
    <v-card class="mx-auto pa-4" elevation="8" width="100%" rounded="lg">
      <v-form ref="form">
        <v-text-field
          :append-inner-icon="'mdi-email-arrow-right-outline'"
          density="compact"
          placeholder="Email address"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          @click:append-inner="sendToEmail"
          v-model="email"
          :rules="[user_emailCheck_rule]"
        ></v-text-field>

        <v-text-field
          v-if="emailVisible"
          :append-inner-icon="'mdi-check'"
          density="compact"
          placeholder="Enter Email Code"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          @click:append-inner="verifiedCode"
          v-model="code"
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
          :append-inner-icon="visible2 ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible2 ? 'text' : 'password'"
          density="compact"
          placeholder="Check your password"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          @click:append-inner="visible2 = !visible2"
          v-model="passwordCheck"
          :rules="[user_pwdCheck_rule]"
        ></v-text-field>

        <v-text-field
          density="compact"
          placeholder="name"
          prepend-inner-icon="mdi-emoticon-outline"
          variant="outlined"
          v-model="name"
          :rules="[user_name_rule]"
        ></v-text-field>

        <v-text-field
          density="compact"
          placeholder="nickname"
          prepend-inner-icon="mdi-star-face"
          variant="outlined"
          v-model="nickname"
          :rules="[user_nickname_rule]"
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
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { userStore } from "@/store/userStore";
import { signupAPI, sendToEmailAPI, verifiedCodeAPI } from "@/api/userAPI";

const store = userStore();
const visible = ref(false);
const visible2 = ref(false);
const form = ref(null);
const emailVisible = ref(false);
const emailCheck = ref(false);
const email = ref("");
const code = ref("");
const password = ref("");
const passwordCheck = ref("");
const name = ref("");
const nickname = ref("");

const sendToEmail = () => {
  sendToEmailAPI(
    email.value,
    ({ data }) => {
      alert(data);
      emailVisible.value = true;
    },
    (error) => {
      console.log(error);
      if (error.response.data.status == 409) {
        alert(error.response.data.message);
      }
    }
  );
};

const verifiedCode = () => {
  const emailCode = {
    email: email.value,
    authCode: code.value,
  };

  verifiedCodeAPI(
    emailCode,
    ({ data }) => {
      if (data) {
        alert("이메일 인증이 완료되었습니다.");
        emailVisible.value = false;
        emailCheck.value = true;
      } else {
        alert("인증번호를 다시 확인해주세요.");
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

function user_emailCheck_rule(v) {
  if (v == null) {
    return "이메일을 입력해주세요.";
  }

  if (v.length == 0) {
    return "이메일을 입력해주세요.";
  }

  if (emailCheck.value == true) {
    return true;
  } else {
    return "이메일을 인증해주세요.";
  }
}

function user_pwdCheck_rule(v) {
  if (v == null) {
    return "패스워드를 입력해주세요.";
  }

  if (v.length == 0 || v !== password.value) {
    return "패스워드가 일치하지 않습니다.";
  } else {
    return true;
  }
}

function user_name_rule(v) {
  if (v == null) {
    return "이름을 입력해주세요.";
  }

  if (v.length == 0) {
    return "이름을 입력해주세요.";
  } else {
    return true;
  }
}

function user_nickname_rule(v) {
  if (v == null) {
    return "닉네임을 입력해주세요.";
  }

  if (v.length == 0) {
    return "닉네임을 입력해주세요.";
  } else {
    return true;
  }
}

const goToLoginPage = () => {
  store.changePageLoginSignup();
};

const signupEvent = async () => {
  const userInput = {
    email: email.value,
    password: password.value,
    name: name.value,
    nickname: nickname.value,
  };

  const validatePromise = await form.value.validate();
  const validate = validatePromise.valid;

  if (emailCheck.value == false) {
    alert("이메일 인증을 완료해주세요.");
  }

  if (validate) {
    signupAPI(
      userInput,
      ({ data }) => {
        let msg = "회원가입이 완료되었습니다. 로그인화면으로 이동합니다.";
        if (data == null) {
          msg = "회원가입이 실패했습니다.";
        }
        if (confirm(msg)) {
          store.changePageLoginSignup();
        }
      },
      (error) => {
        console.log(error);
      }
    );

    code.value = "";
    await form.value.reset();
  }
};
</script>
