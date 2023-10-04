from django.contrib import admin
from django.urls import path, include
from rest_framework import routers
from recommendations.views import RecommendPeripheral

router = routers.DefaultRouter()
urlpatterns = [
    path('', include(router.urls)),
    path('v1/recommends/<str:peripheral>/<int:user_id>/', RecommendPeripheral.as_view(), name='recommend_peripheral'),
]