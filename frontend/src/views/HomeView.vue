<script setup>
  import { RouterLink, RouterView } from 'vue-router'
</script>


<template>
  <main>
    <div class="banner">
      <img alt="BannerImg" class="bannerImg" src="@/assets/main-banner.jpg" />
      <div class="box">
        <div class="bannerComment">
          <span>
            PC.GG는 빅 데이터를 이용해서 사용자에게 최적화된 견적을 제공하는걸 목표로 합니다
          </span>
        </div>
      </div>
      <div class="button-tap-up flex-center ">
        <v-btn class="estimate text-2" color="#4599FC" size="x-large"
         :style="{height: '5.0625rem'}">견적 받으러 가기</v-btn>
         <RouterLink to="/recommendation">
          <v-hover>
            <template v-slot:default="{ isHovering, props }">
              <div class="rangking text-1 flex-center "
                    size="x-large"
                    v-bind="props"
                    :style="isHovering
                    ?
                    { height:'6rem' ,background:'#F9D13E'}
                    :
                    {background:'#706E5E'} "
                    style="cursor: pointer"
                    >
                    <div class="flex-center">
                      {{ !isHovering
                        ?
                        '견적랭킹'
                        :
                        'Ranking'
                        }}
                      <img v-if="isHovering" src="@/assets/Ranking.png" alt="Ranking">
                    </div>

              </div>
            </template>
          </v-hover>
        </RouterLink>

      </div>

      <div class="button-tap-down">
        <v-container>
          <v-row>
            <v-col>
              <RouterLink to="/share">
                <v-btn class="share text-2" size="x-large">공유마당</v-btn>
             </RouterLink>
            </v-col>
            <v-col>
              <RouterLink to="/peripheral">
                <v-btn class="peripherals text-2" size="x-large">주변기기</v-btn>
              </RouterLink>
            </v-col>
            <v-col>
              <v-btn class="used-market text-2" size="x-large">중고 장터</v-btn>
            </v-col>
            <v-col>
              <v-btn class="simulation text-2" size="x-large">시뮬레이션</v-btn>
            </v-col>
          </v-row>
        </v-container>

      </div>
    </div>
  </main>
</template>



<style>
.banner {
  position: relative; /* 부모 요소에 상대적 위치를 설정합니다. */
  width: 100%;
  height: 20rem;
}

.bannerImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.box {
  position: absolute; /* 부모 요소에 대해 절대 위치를 설정합니다. */
  top: 50%; /* 상단 중앙에 정렬합니다. */
  left: 50%; /* 좌측 중앙에 정렬합니다. */
  transform: translate(-50%, -50%); /* 중앙 정렬을 위한 변형을 적용합니다. */
  width: 60.5625rem;
  height: 8.6875rem;
  text-align: center;
  color: #fff;
  text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

}

.bannerComment {
  text-align: center;
  font-family: Inter;
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;

}


</style>


<style scoped>

.estimate {
  width: 31.375rem;
  height: 5.0625rem;
  flex-shrink: 0;

  border-radius: 0.5rem;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  margin-left: 5%;

}

.share, .peripherals ,.used-market, .simulation {
  width: 15.625rem;
  height: 6.25rem;
  flex-shrink: 0;

  border-radius: 0.9375rem;
  color:white;
  background: rgba(112, 110, 110, 0.70);

}

.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;

}

.rangking {
  width: 6.25rem;
  height: 3.75rem;
  flex-shrink: 0;
  background-color: #F9D13E;
  color : white;

  border-radius: 0.5rem;
  margin-left: 100%;
}


.button-tap-up {
  height: 12rem;

}

.text-1 {
  color: var(--Color, #FFF);
  font-family: Plus Jakarta Sans;
  font-size: 1rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.text-2 {
  color: var(--Color, #FFF);
  text-align: center;
  font-family: Inter;
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

</style>

