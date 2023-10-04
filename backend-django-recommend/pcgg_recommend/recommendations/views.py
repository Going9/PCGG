from django.shortcuts import render

# Create your views here.
import random
import pandas as pd
import statistics
# import matplotlib.pyplot as plt
# %matplotlib inline
from sklearn.metrics.pairwise import cosine_similarity
import operator
from recommendations.models import PeripheralReview, Keyboard, Printer, Monitor, Mouse
from rest_framework.views import APIView
from rest_framework.response import Response
from .utils import similar_users, recommend_item
from .serializers import KeyboardSerializer, PrinterSerializer, MonitorSerializer, MouseSerializer


class RecommendPeripheral(APIView):
    def get(self, request, peripheral, user_id):
        # 로직 처리
        peripherals = PeripheralReview.objects.filter(type=peripheral)
        # rating.drop(['id', 'type', 'created_at', 'review'], axis=1, inplace=True)

        # "id"와 "type"을 제외한 필드만 선택하여 새로운 쿼리셋 생성
        rating = peripherals.values('peripheral_id', 'user_id', 'rating')
        rating = pd.DataFrame(rating)

        # 콘텐츠를 평가한 유저 숫자
        rating_per_user = rating.groupby('user_id')['rating'].count()
        statistics.mean(rating_per_user.tolist())

        # 콘텐츠별 평가된 평점 등급
        ratings_per_peripheral = rating.groupby('peripheral_id')['rating'].count()
        statistics.mean(ratings_per_peripheral.tolist())

        # 피벗테이블 만들기 + 결측치 제거
        rating_matrix = rating.pivot_table(
            index='user_id',
            columns='peripheral_id',
            values='rating'
        ).fillna(0)

        # userId = 3에 대한
        similar_user_indices = similar_users(user_id, rating_matrix)

        # 추천 주변기기id 뽑아내기
        recommend_content = recommend_item(user_id, similar_user_indices, rating_matrix)

        # 최종 추천 리스트
        recommend_peripheral = []

        # peripheral과 일치하는 모델 찾기
        model_class = None
        serializer_class = None

        if peripheral == 'keyboard':
            model_class = Keyboard
            serializer_class = KeyboardSerializer
        elif peripheral == 'printer':
            model_class = Printer
            serializer_class = PrinterSerializer
        elif peripheral == 'monitor':
            model_class = Monitor
            serializer_class = MonitorSerializer
        elif peripheral == 'mouse':
            model_class = Mouse
            serializer_class = MouseSerializer

        if model_class and serializer_class:
            for peripheral_id in recommend_content:
                peripheral_instance = model_class.objects.get(id=peripheral_id)
                serialized_peripheral = serializer_class(peripheral_instance)
                recommend_peripheral.append(serialized_peripheral.data)

        return Response(recommend_peripheral)
