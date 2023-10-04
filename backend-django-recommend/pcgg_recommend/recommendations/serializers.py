from rest_framework import serializers
from recommendations.models import Keyboard, Printer, Monitor, Mouse


class KeyboardSerializer(serializers.ModelSerializer):
    class Meta:
        model = Keyboard
        fields = '__all__'


class PrinterSerializer(serializers.ModelSerializer):
    class Meta:
        model = Printer
        fields = '__all__'


class MonitorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Monitor
        fields = '__all__'


class MouseSerializer(serializers.ModelSerializer):
    class Meta:
        model = Mouse
        fields = '__all__'
